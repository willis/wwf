<?php
/*!
	@file
	Inner classes, which do common tasks. No one of them is purposed for direct usage. 
*/
 
/*! Class which allows to assign|fire events.
*/
class EventMaster{
	private $events;//!< hash of event handlers
	
	
	/*! constructor
	*/
	function __construct(){
		$this->events=array();
	}
	/*! Method check if event with such name already exists.
		@param name
			name of event, case non-sensitive
		@return
			true if event with such name registered, false otherwise
	*/	
	public function exist($name){
		$name=strtolower($name);
		return isset($this->events[$name]);
	}
	/*! Attach custom code to event.
	
		Only on event handler can be attached in the same time. If new event handler attached - old will be detached.
		
		@param name
			name of event, case non-sensitive
		@param method
			function which will be attached. You can use array(class, method) if you want to attach the method of the class.
	*/
	public function attach($name,$method){
		$name=strtolower($name);
		$this->events[$name]=$method;
	}
	/*! Detach code from event
		@param	name
			name of event, case non-sensitive
	*/	
	public function detach($name){
		$name=strtolower($name);
		unset($this->events[$name]);
	}
	/*! Trigger event.
		@param	name
			name of event, case non-sensitive
		@param data
			value which will be provided as argument for event function,
			you can provide multiple data arguments, method accepts variable number of parameters
		@return 
			true if event handler was not assigned , result of event hangler otherwise
	*/
	public function trigger($name,$data){
		$arg_list = func_get_args();
		$name=strtolower(array_shift($arg_list));
		
		if (!isset($this->events[$name]))
			return true;
		return  call_user_func_array($this->events[$name], $arg_list);
	}
}

/*! Class which handles access rules.	
**/
class AccessMaster{
	private $rules,$local;
	/*! constructor
	
		Set all access right to "allowed" by default ( read, insert, updated, delete )
	*/
	function __construct(){
		$this->rules=array("read" => true, "insert" => true, "update" => true, "delete" => true);
		$this->local=true;
	}
	/*! change access rule to "allow"
		@param name 
			name of access right
	*/
	public function allow($name){
		$this->rules[$name]=true;
	}
	/*! change access rule to "deny"
		
		@param name 
			name of access right
	*/
	public function deny($name){
		$this->rules[$name]=false;
	}
	/*! check access rule
		
		@param name 
			name of access right
		@return 
			true if access rule allowed, false otherwise
	*/
	public function check($name){
		if ($this->local){
			/*!
			todo
				add referrer check, to prevent access from remote points
			*/
		}
		if (!isset($this->rules[$name]) || !$this->rules[$name]){
			return false;
		}
		return true;
	}
}

class SQLMaster{
	private $sqls; //!< has of sql queries
	private $db; //!< database object
	public $config; //!< configuration of dataset
	private $confirm; //!< flag of auto-insert mode
	/*! constructor
		
		@param db 
			database object
	*/
	function __construct($db){
		$this->sqls=array();
		$this->db=$db;
		$this->confirm=false;
	}
	
	/*! add field to dataset config
	
		added field will be used in all auto-generated queries
		@param name 
			name of field
		@param aliase
			aliase of field, optional
	*/	
	public function add_field($name,$aliase=false){
		if ($aliase===false) $aliase=$name;
		
		$ind = $this->is_field($name);
		if ($ind!=-1) return;
		array_push($this->config["field"],array($name,$aliase));
		
	}
	/*! remove field from dataset config
	
		removed field will be excluded from all auto-generated queries
		@param name 
			name of field, or aliase of field
	*/
	public function remove_field($name){
		$ind = $this->is_field($name);
		if ($ind==-1) return;
		array_splice($this->config["field"],$ind,1);
	}
	/*! check if field is a part of dataset
		
		@param name 
			name of field
		@return 
			returns true if field already a part of dataset, otherwise returns true
	*/
	private function is_field($name){
		for ($i=0; $i<sizeof($this->config["field"]); $i++)
			if ($this->config["field"][$i][0]==$name || $this->config["field"][$i][1]==$name )	return $i;
		return -1;
	}
	/*! enable auto insert mode
	
		in auto insert mode, connector checks result of update operation and 
		if it was not successful ( there are no affected records ), then same operation 
		repeated in insert mode
		
		@param mode 
			boolean flag, optional, true by default
	*/
	public function auto_insert($mode=true){
		$this->confirm=$mode;
	}
	/*! assign config structure
		
		@param config 
			config structure
	*/
	public function config($config){
			$this->config=$config;
	}
	/*! assign named sql query
		
		@param name 
			name of sql query
		@param data
			sql query text
	*/
	public function attach($name,$data){
		$name=strtolower($name);
		$this->sqls[$name]=$data;
	}
	/*! retrieve named sql query
		
		query will have replaced all occurenses of named parameters
		@param name 
			name of sql query
		@param rid
			id of record
		@param data 
			data hash of record
		@return 
			sql string
	*/
	public function get($name,$rid,$data){
		$name=strtolower($name);
		if (!$this->sqls[$name]) return true;
		
		$str=str_replace("{".$this->config["id"][1]."}",$this->db->escape($rid),$this->sqls[$name]);
		for ($i=0; $i < sizeof($this->config["field"]); $i++) {
			$step=$this->config["field"][$i][1];
			$str=str_replace("{".$step."}",$this->db->escape($data[$step]),$str);
		}
		return $str;
	}
	/*! generates confirmation sql
		
		@param rid 
			id of record
		@return 
			sql string, which validates that element with defined ID exist
	*/
	public function confirm_sql($rid){
		if (!$this->confirm) return false;
		
		$id=$this->config["id"][0];
		$table=$this->config["table"][0];

		$sql="SELECT ".$id." FROM ".$table." WHERE ".$id."=".$this->db->escape($rid);
		return $sql;
	}
	/*! generates update sql
		
		@param rid 
			id of record
		@param data
			hash of object's data
		@return 
			sql string, which updates record with provided data
	*/
	public function update_sql($rid,$data){
		$sql="UPDATE ".$this->config["table"][0]." SET ";
		$temp=array();
		for ($i=0; $i < sizeof($this->config["field"]); $i++) { 
			$step=$this->config["field"][$i][0];
			$step_name=$this->config["field"][$i][1];
			$temp[$i]= $step."='".$this->db->escape($data[$step_name])."'";
		}
		$sql.=implode(",",$temp)." WHERE ".$this->config["id"][0]."='".$this->db->escape($rid)."'";
		return $sql;
	}
	/*! generates delete sql
		
		@param rid 
			id of record
		@param data
			hash of data
		@return 
			sql string, which delete record 
	*/
	public function delete_sql($rid,$data){
		$sql="DELETE FROM ".$this->config["table"][0];
		$sql.=" WHERE ".$this->config["id"][0]."='".$this->db->escape($rid)."'";
		return $sql;
	}
	/*! generates insert sql
		
		@param rid 
			id of record
		@param data
			has of data
		@param id
			id which will be used for new record, optional 
		@return 
			sql string, which inserts new record with provided data
	*/
	public function insert_sql($rid,$data,$id=false){
		$temp=array(); foreach($this->config["field"] as $k => $v) $temp[$k]=$v[0];
		if ($id) $temp[]=$this->config["id"][0];
		
		$sql="INSERT INTO ".$this->config["table"][0]."(".implode(",",$temp).") VALUES ";
		$temp=array(); 
		for ($i=0; $i < sizeof($this->config["field"]); $i++) { 
			$temp[$i]= "'".$this->db->escape($data[$this->config["field"][$i][1]])."'";
		}
		if ($id) $temp[]="'".$this->db->escape($id)."'";
		
		$sql.="(".implode(",",$temp).")";
		return $sql;
	}
}
/*! Controls error and debug logging.
**/
class LogMaster{
	private $_log;//!< logging mode flag
	private $session;//!< all messages generated for current request
	/*! constructor
	*/
	function __construct(){
		$this->mode=false;
		$this->session="";
	}		
	/*! convert array to string representation ( it is a bit more readable than var_dump )
		
		@param data 
			data object
		@param pref
			prefix string, used for formating, optional
		@return 
			string with array description
	*/
	private function log_details($data,$pref=""){
		if (is_array($data)){
			$str=array("");
			foreach($data as $k=>$v)
				array_push($str,$pref.$k." => ".$this->log_details($v,$pref."\t"));
			return implode("\n",$str);
   		}
   		return $data;
	}
	/*! put record in log
		
		@param str 
			string with log info, optional
		@param data
			data object, which will be added to log, optional
	*/
	public function log($str="",$data=""){
		if ($this->_log){
			$message = $str.$this->log_details($data)."\n\n";
			$this->session.=$message;
			error_log($message,3,$this->_log);			
		}
	}
	/*! get logs for current request
		@return 
			string, which contains all log messages generated for current request
	*/
	public function get_session_log(){
		return $this->session;
	}
	/*! error handler, put normal php errors in log file
		
		@param errn
			error number
		@param errstr
			error description
		@param file
			error file
		@param line
			error line
		@param context
			error cntext
	*/
	public function error_log($errn,$errstr,$file,$line,$context){
		$this->log($errstr." at ".$file." line ".$line);
	}
	/*! enable logging

		@param $name 
			path to the log file, if false provided as valut - logging will be disabled
	*/
	public function enable_log($name){
		$this->_log=$name;
		if ($this->_log){
			set_error_handler(array($this,"error_log"),E_ALL ^ E_NOTICE);
			$this->log("\n\n====================================\nLog started, ".date("d/m/Y h:m:s")."\n====================================");
		}
	}
}

?>