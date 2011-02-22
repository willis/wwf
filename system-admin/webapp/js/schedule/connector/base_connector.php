<?php
/*!
	@file
		base connector classes used by all public connectors
*/

require_once("db_mysql.php");
require_once("tools.php");

//enable buffering to catch and ignore any custom output before XML generation
//because of this command, it strongly recommended to include connector's file before any other libs
//in such case it will be handle any extra output from not well formed code in them
ini_set("output_buffering","On");
ob_start();

/*! base class for component item representation	
**/
class DataItem{
	protected $id; //!< id of element
	protected $name;//!< hash of data names
	protected $data;//!< hash of data
	protected $index;//!< index of element
	protected $skip;//!< flag , which set if element need to be skiped during rendering
	/*! constructor
		
		@param id 
			id of element
		@param data
			hash of data
		@param name
			hash of data names
		@param index
			index of element
	*/
	function __construct($id,$data,$name,$index){
		$this->id=$id;
		$this->name=$name;
		$this->data=$data;
		$this->index=$index;
		$this->skip=false;
	}
	/*! get named value
		
		@param name 
			name or alias of field
		@return 
			value from field with provided name or alias
	*/
	public function get_value($name){
		return $this->data[$name];
	}
	/*! set named value
		
		@param name 
			name or alias of field
		@param value
			value for field with provided name or alias
	*/
	public function set_value($name,$value){
		return $this->data[$name]=$value;
	}
	/*! get id of element
		@return 
			id of element
	*/
	public function get_id(){
		return $this->id;
	}
	/*! change id of element
		
		@param value 
			new id value
	*/
	public function set_id($value){
		$this->id=$value;
	}
	/*! get index of element
		
		@return 
			index of element
	*/
	public function get_index(){
		return $this->index;
	}
	/*! mark element for skiping ( such element will not be rendered )
	*/
	public function skip(){
		$this->skip=true;
	}
}

/*! base connector class

	Has not practical usage except of storing methods common for all connectors
**/
class Connector {
	public $db; //!< DBWrapper object
	public $config; //!< configuration hash
	public $sql; //!< SQLMaster object
	public $event; //!< EventMaster object
	public $access;  //!< AccessMaster object
	protected $_log; //!< LogMaster object
	private $exec_time; //!< execution time counter
	protected $dload;//!< flag of dyn. loading mode
	protected $output;//!< output buffer
	protected $form;//!< FormConnector object, if any
	protected $client_log;//!< flag of client log output
	protected $dbtype;//!< type of database as string ( MySQL of Postgre )
	protected $editing;//!< flag of edit mode ( response for dataprocessor )
	protected $encoding;//!< assigned encoding (UTF-8 by default) 
	
	/*! constructor
		
		Here initilization of all Masters occurs, execution timer initialized
		@param db 
			db connection resource
		@param type
			string , which hold type of database ( MySQL or Postgre ), optional
	*/
	public function __construct($db,$type="MySQL"){
		$this->config = array();

		$this->_log = new LogMaster();
		$this->event = new EventMaster();
		$this->access = new AccessMaster();
		
		$this->dbtype=$type;
		$type.="DBWrapper";
		$this->db=new $type($db,$this->_log);
		$this->sql = new SQLMaster($this->db);
		

		$this->dload = false;
		
		$this->output = "";
		$this->form = false;
		$this->client_log=true;
		$this->encoding="UTF-8";
		
		$this->exec_time=microtime(true);
	}
	/*! connect form to the existing connector (not implemented)
		
		@param form 
			FormConnector object
		@param form_name
			name of form tag on HTML page
	*/
	public function attach_form($form, $form_name){
		$this->form=array("obj"=>$form,"name"=>$form_name);
	}
	/*! set xml encoding
		
		methods sets only attribute in XML, no real encoding conversion occurs	
		@param encoding 
			value which will be used as XML encoding
	*/
	public function set_encoding($encoding){
		$this->encoding=$encoding;
	}
	/*! end processing
	
		stop execution timer, kill the process
	*/
	protected function end_run(){
		$time=microtime(true)-$this->exec_time;
		$this->_log->log("Done in {$time}ms");
		flush();
		die();
	}
	/*! extract alias part from SQL compatible name
		longer description
		@param data 
			field name as a string
		@return 
			name as valid alias
	*/
	protected function set_alias($data){ 
			$data=preg_split("/\\(|\\)/i",$data);
			if (sizeof($data)==1){
				$temp=explode(".",$data[0]);
				$data[1]=$temp[sizeof($temp)-1];
			}
			return $data;
	}
	/*! parse config strings to receive valid names and aliases of configuration params
		longer description
		@param name 
			name of config entry
		@param str
			string which will be parsed
		@param array_mode
			count incoming data as single parameter or as an array of parameres
	*/
	protected function set_config($name,$str,$array_mode=false){
		 if ($array_mode){
		 	$data=explode(",",$str);
		 	foreach($data as $k=>$v)
		 		$data[$k]=$this->set_alias($v);
	 	} else
			$data = $this->set_alias($str);
			
		$this->config[$name]=$data;		
	}
	/*! return DBQuery object for active DB
		
		@param sql 
			sql string, optional
		@return 
			DBQuery object
	*/
	protected function get_query($sql=""){
		if (!$this->query){
			$query_class="DBQuery".$this->dbtype;
			$this->query = new $query_class($sql);
		}
		return $this->query;
	}
	/*! prepare DBQuery based on config
	*/
	public function fill_query(){
		if (!$this->access->check("read")){
			$this->_log->log("Access control: read operation blocked");
			return $this->output_error();
		}
			
		$this->log("Ready for SQL generation",$this->config);
   		$this->get_query()->fill($this->config,$this->event);
	}
	/*! enable or disable dynamic loading mode
		
		@param count 
			count of rows loaded from server, actual only for grid-connector, can be skiped in other cases. 
			If value is a false or 0 - dyn. loading will be disabled
	*/
	public function dynamic_loading($count){
		$this->dload=$count;
	}
	/*! output error message in stdout
	*/
	protected function output_error(){	
		$this->log("Critical error in Connector, processing stoped.");
		header("Content-type:text/html");
		if ($this->client_log)
			echo "<pre><xmp>\n".$this->_log->get_session_log()."\n</xmp></pre>";
		else
			echo "Error in Connector\nCheck server side logs for more details.";
		
		die();
	}
	/*! output xml headers
	
		method attempts to clear all previously outputed format
	*/	
	protected function output_header(){
		ob_clean();
		header("Content-type:text/xml");
		echo "<?xml version='1.0' encoding='".$this->encoding."' ?>";
	}	
	
	/*! enable logging
		
		@param path 
			path to the log file. If set as false or empty strig - logging will be disabled
		@param client_log
			enable output of log data to the client side
	*/
	public function enable_log($path=true,$client_log=false){
		$this->_log->enable_log($path);
		$this->client_log=$client_log;
	}
	/*! provides infor about current processing mode
		@return 
			true if processing dataprocessor command, false otherwise
	*/
	public function is_select_mode(){
		$this->parse_request();
		return !$this->editing;
	}
	
	/*! log some info in error log
		
		@param str 
			sting data which will be logged
		@param data
			additional data , which will be attached to log record
	*/
	protected function log($str,$data=""){
		$this->_log->log($str,$data);
	}	
	
	
	//virtual methods
	
	/*! parse incoming request, detects commands and modes
	*/
	function parse_request(){
	}

	/*! customize config by rules and modes from incoming request
	*/
	function customize_config(){
		$this->config["rules"]=array();
		$this->config["rules"][]=array($this->config["id"][0],$this->db->escape($this->id),"=");
	}
	
	/*! config connector based on table
		
		@param table 
			name of table in DB
		@param id 
			name of id field
		@param field
			list of fields names
	*/
	function render_table($table,$id,$field){
	}
	
	/*! render self
	
		process commands, output requested data as XML
	*/
	function render(){
	}
	
	/*! config connector based on table
		
		@param sql 
			sql query used as base of configuration
		@param id 
			name of id field
		@param field
			list of fields names
	*/
	function render_sql($sql,$id,$field){
	}
		
	/*! render from DB resultset
		@param res
			DB resultset
		process commands, output requested data as XML
	*/
	function render_set($res){
	}
	
	/*! output fetched data as XML
	*/
	function output_xml(){
	}
	
}
?>