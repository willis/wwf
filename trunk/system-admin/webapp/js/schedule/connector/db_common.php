<?php
/*!
	@file
	Virtual classes for database operations
*/


/*! generator of custom select sql queries
	
	While base class provide enough functionality to work with any SQL compilant database, 
	connectors never use it directly, but creates child classes, specific for each DB type. 
**/
class DBQuery{
	public $select; //!< select statement
	public $table; //!< table statement
	public $order;//!< order statement
	public $rules;//!< where statement
	public $from;//!< start position 
	public $count;//!< count of records requested
	public $group_by;//!< group statement
	private $old_rules;	//!< used for storing initil set of rules
	
	/*! constructor
	
		object can be created empty, or on base of existing sql query
		@param str 
			sql query from which initial data will be taken
	*/
	public function __construct($str=""){
		$this->select="";
		$this->order="";
		$this->table="";
		$this->from=0;
		$this->count=0;
		$this->rules=array();
		if ($str)
			$this->parse($str);
		$this->init();
	}
	/*! prepare internal state for sql generation
	*/
	protected function init(){
		$this->prefix="";
	}
	/*! convert self to the string, used for logging
	*/
	public function __toString(){
		return "select:{$this->select}; table:{$this->table};";
	}
	/*! make a copy of itself
		@return 
			DBQuery object with same properties
	*/
	public function copy(){
		$temp = new DBQuery();
		$temp->select=$this->select;
		$temp->order=$this->order;
		$temp->table=$this->table;
		$temp->from=$this->from;
		$temp->group_by=$this->group_by;
		$temp->count=$this->count;
		$temp->rules=$this->rules;
		$temp->old_rules=$this->old_rules;
		
		return $temp;
	}
	/*! parse sql string and fill internal state based on it

		@param str 
			string which need to be parsed
	*/
	public function parse($str){
		$data = preg_split("/from/i",$str);
		
		$this->select = preg_replace("/select/i","",$data[0]);
		$data=$data[1]; //after select part

	  	$data = preg_split("/where/i",$data);
      	if (sizeof($data)>1){ //where construction exists
			$this->table = $data[0];
			$data[0]=$data[1];
		}

		$data = preg_split("/order[ ]+by/i",$data[0]);
		if (!$this->table)
			$this->table=$data[0];
		else $this->rules[] = $data[0];
		$this->order = $data[1];		
	}
	/*! generates sql query based on current state
		@return 
			sql string
	*/
	public function sql(){ 
		$str="SELECT ".$this->select." FROM ".$this->table;
		if (sizeof($this->rules))
			$str.=" WHERE ".implode(" AND ",$this->rules);
		if ($this->group_by)
			$str.=" GROUP BY ".$this->group_by;
		if ($this->order)
			$str.=" ORDER BY ".$this->order;
		if ($this->count)
			$str.=" LIMIT ".$this->from.",".$this->count;
		return $str;
	}
	/*! fill internal state based on config

		@param config 
			config object
		@param event 
			EventMaster object
	*/
	public function fill($config,$event=false){
		if ($config["id"][0]!="dhx_auto_generated_id") 
			$actual_id=$config["id"];
		else
			$actual_id="UUID() as dhx_auto_generated_id";
			
   		if (!$this->select)
   			$this->set_select($actual_id,$config["field"]);
   		if (!$this->table)
   			$this->set_table($config["table"]);
   			
   		if ($config["sort"])
   			$this->set_order($config["sort"],$config["direction"],$event);
   		if ($config["rules"])
   			$this->set_rules($config["rules"],$event);
   		if ($config["count"])
   			$this->set_count($config["count"],$config["from"]);
	}
	/*! construct select part of query
		
		method can accept any number of params, which will be threated as array(name,aliase) objects
	*/
	public function set_select(){
		$numargs = func_num_args();
		$temp=array();
		for ($i=0; $i < $numargs; $i++) {
			$arg=func_get_arg($i);
			if (is_array($arg) && is_array($arg[0]))
				$temp=array_merge($temp,$arg);
			else
				$temp[]=$arg;
		}
		for ($i=0; $i < sizeof($temp); $i++) {  
			if (is_array($temp[$i]))
				if ($temp[$i][0]==$temp[$i][1])
					$temp[$i]=$temp[$i][0];
				else
					$temp[$i]=$temp[$i][0]." as ".$temp[$i][1];
		}
		$this->select = implode(" , ",$temp);
	}
	/*! set from part of query

		@param data 
			array with (name,aliase) of table
	*/
	public function set_table($data){
		if (is_array($data))
			$this->table=$data[0];
		else
			$this->table=$data;
	}
	/*! set order by part of query
		
		@param data 
			array with (name,aliase) of sorting field
		@param direction
			sorting directtion, string, can be ASC or DESC
		@param event
			EventMaster object, optional 
	*/
	public function set_order($data,$direction='ASC',$event=false){
		if (!$direction) $direction="ASC";
		if (is_array($data)) $data=$data[1];
		if ($event){
			$check = $event->trigger("beforeSort",$data,$direction);
			if ($check !== true) 
				return $this->order=$check;
		}
		$this->order=$data." ".$direction;
	}
	/*! save current set of rules for later usage
	*/
	public function save_rules(){
		$this->old_rules=$this->rules;
	}
	/*! restore previously saved set of rules for later usage
	*/
	public function restore_rules(){
		$this->rules = $this->old_rules;
	}
	/*! set filtering rules for query ( where section )
		
		@param rule 
			array of rules, structure is
				- array(name,value,operation)
				- array(nave,value) - LIKE %x% will be used as operation
		@param event
			EventMaster object, optional
	*/
	public function set_rules($rule,$event=false){
		if (!is_array($rule)) $rule=array($rule);
		foreach ($rule as $k => $v){
			if ($event){
				$check = $event->trigger("beforeFilter",$v[0],$v[1]);
				if ($check!==true){
					$this->rules[]=$check;
					continue;
				}
			}
			if ($v[2])
				$this->rules[]=$v[0].$v[2]."'".$v[1]."'";
			else
				$this->rules[]=$v[0]." LIKE '%".$v[1]."%'";
		}	
	}
	/*! set limit part of query
		
		@param count 
			count of requested records
		@param from
			set start position of query, optional starts from 0 by default
	*/
	public function set_count($count,$from=0){
		if (!$from) $from=0;
		$this->count=$count;
		$this->from=$from;
	}
}

/*! Wrapper around database
	
	Pure virtual class. Functionality must be defined in child classes. 
**/
class DBWrapper{
	protected $db; //!< db connection resource
	protected $logger;//!< LogMaster object
	/*! constructor
		@param db 
			db resource
		@param logger
			LogMaster object
	*/
	public function __construct($db,$logger){
		$this->db=$db;
		$this->logger=$logger;
	}
	/*! exec sql query
		
		if result specified, function takes data from specified 
		column of first row in dataset and returns it
		@param sql 
			sql string
		@param result
			optional, index of column, from which data need to be returned
		@return 
			return sql result resource or data based on second parameter
	*/
	function query($sql,$result=false){ 
		$this->virtual_error(__METHOD__); }
	/*! escapes data according DB rules
		
		@param str 
			string of data
		@return 
			escaped string of data
	*/
	function escape($str){ 
		$this->virtual_error(__METHOD__); }
	/*! return array of data from dataset
	
		@param res
			sql result resource
		@return 
			array of data
	*/
	function get_data_array($res){ 
		$this->virtual_error(__METHOD__); }
	/*! return hash of data from dataset
		
		@param res 
			sql result resource
		@return 
			hash of data
	*/
	function get_data_named($res){ 
		$this->virtual_error(__METHOD__); }
	/*! return id of previously inserted record
		
		@return 
			string with ID value
	*/
	function get_id(){ 
		$this->virtual_error(__METHOD__); }
	/*! return count of records affected by previou operation
		
		@param res 
			sql result resource
		@return 
			count of records affected by previou operation
	*/
	function count($res){
		$this->virtual_error(__METHOD__); }
	 
	/*! check that class method correctly re-defined
	
		probably there are better ways to implement the same use-case
		
		@param method 
			name of method
	*/
	private function virtual_error($method){
		trigger_error("Method {$method} nod defined for DB class","E_USER_ERROR");
	 }
}


?>