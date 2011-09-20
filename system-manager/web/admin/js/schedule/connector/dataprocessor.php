<?php
/*! Base DataProcessor handling
**/
class DataProcessor{
	protected $event;//!< EventMaster object
	protected $sql;//!< SQLMaster object
	protected $access;//!< AccessMaster object
	protected $db;//!< DBWrapper object
	protected $logger;//!< LogMaster object
	/*! constructor
		
		@param db 
			DBWrapper object
		@param logger
			LogMaster object
		@param event
			EventMaster object
		@param sql
			SQLMaster object
		@param access
			AccessMaster object
	*/
	function __construct($db,$logger,$event,$sql,$access){
		$this->db = $db;
		$this->logger=$logger;
		$this->event=$event;
		$this->sql=$sql;
		$this->access=$access; 
	}
	
	/*! converts status string to the inner mode
		
		@param status 
			external status string
		@return 
			inner mode name
	*/
	protected function status_to_mode($status){
		switch($status){
			case "updated":
				return "update";
				break;
			case "inserted":
				return "insert";
				break;
			case "deleted":
				return "delete";
				break;
			default:
				return $status;
				break;
		}
	}
	/*! process data updated request received
		
		@param status 
			string with name of procession mode
		@param id 
			id of record to be processed
		@param data
			hash of record related data 
		@param master
			master record, optional
		@return 
			DataAction object with details of processing
	*/
	protected function inner_process($status,$id,$data,$master=false){
		$action = new DataAction($status,$id,$data,$master);
		$action->assign_logger($this->logger);
		$results[]=$action;
		$mode = $this->status_to_mode($status);
		
		if (!$this->access->check($mode)){
			$this->logger->log("Access control: {$operation} operation blocked");
			$action->error();
		} else {
			$check = $this->event->trigger("beforeProcessing",$action);
			if (!$action->is_ready()){
				$this->check_exts($action,$mode);
				$action->sync_config($this->sql);
				if (!$action->is_ready())
				 	switch($status){
						case "inserted":
							$this->data_insert($action);
							break;
						case "deleted":
							$this->data_delete($action);
							break;
						case "updated":
							$this->data_update($action);
							break;
						default:
							$this->logger->log("Unknown action ",$status);
							break;
				}
			}
			$check = $this->event->trigger("afterProcessing",$action);
		}
		return $action;
	}
	/*! check if some event or sql code intercepts processing

		@param action 
			DataAction object
		@param mode
			name of inner mode ( will be used to generate event names )
	*/
	function check_exts($action,$mode){
		$check = $this->event->trigger("before".$mode,$action);	
		if ($action->is_ready()){
			$this->logger->log("Event code for ".$mode." processed");
		} else {
			$sql=$this->sql->get($mode,$action->get_id(),$action->get_data());
			if ($sql!==true){
				//exec sql
				$this->logger->log("SQL code for ".$mode." mastered from template");
				$this->db->query($sql);
				if ($mode=="inserted")
					$action->success($this->db->get_id());
				else
					$action->success();	
			}
		}
	}
	
	/*! execute update action

		If confirmation mode enabled and no record was affected by update, insert action executed
		@param  action
			DataAction object
	*/
	function data_update($action){
		
		$confirm = $this->sql->confirm_sql($action->get_id());
		if ($confirm){
			if (!$this->db->count($this->db->query($confirm))){
				if ($this->access->check("insert")){
					$action->set_status("inserted");
					$this->check_exts($action,"insert");
					if (!$action->is_ready())
						$this->data_insert($action);
					return;
				} else
					$action->error();
			}
		}
		
		$res=$this->db->query($this->sql->update_sql($action->get_id(),$action->get_data()));
		if (!$res) 
			return $action->error();
		$action->success();
	}
	/*! execute delete action

		@param  action
			DataAction object
	*/	
	function data_delete($action){
		$res=$this->db->query($this->sql->delete_sql($action->get_id(),$action->get_data()));
		if (!$res) 
			return $action->error();
		else
			return $action->success();
	}
	/*! execute insert action

		@param  action
			DataAction object
	*/	
	function data_insert($action){
		$res=$this->db->query($this->sql->insert_sql($action->get_id(),$action->get_data(),$action->master_id()));
		if (!$res)
			return $action->error();
		else
			return $action->success($this->db->get_id());
	}		
	
	/*! output xml response for dataprocessor

		@param  results
			array of DataAction objects
	*/
	function output_edit($results){
		$this->logger->log("Edit operation finished",$results);
		ob_clean();
		header("Content-type:text/xml");
		echo "<?xml version='1.0' ?>";
		
		echo "<data>";
		for ($i=0; $i < sizeof($results); $i++)
			echo $results[$i]->to_xml();
		echo "</data>";
	}		
	
		//virtual methods
	
	/*! converts POST request to ID based hash of records
		longer description
		@param ids 
			list of possible ID values, expected in request
		@return 
			hash of records
	*/
	function get_post_values($ids){
	}
	/*! process incoming request
	
		execute actions for all incoming data, print XML report for client side code
		@param form 
			linked FormConnector, optional
	*/
	function process($form=false){
	}
	/*! convert array of data to named hash, based on protocol conventions
		
		@param data 
			array of data
		@return 
			hash of data
	*/
	function name_data($data){
	}
	
}

/*! contain all info related to action and controls customizaton
**/
class DataAction{
	private $status; //!< cuurent status of record
	private $id;//!< id of record
	private $data;//!< data hash of record
	private $userdata;//!< hash of extra data , attached to record
	private $nid;//!< new id value , after operation executed
	private $output;//!< custom output to client side code
	private $attrs;//!< hash of custtom attributes
	private $ready;//!< flag of operation's execution
	private $master;//!< master DataActon object
	private $addf;//!< array of added fields
	private $delf;//!< array of deleted fields
	private $logger;//!< LogMaster object
	
	/*! constructor
		
		@param status 
			current operation status
		@param id
			record id
		@param data
			hash of data
		@param master
			master DataAction object
	*/
	function __construct($status,$id,$data,$master=false){
		$this->status=$status;
		$this->id=$id;
		$this->data=$data["data"];
		$this->userdata=$data["original"];
		$this->nid=$id;
		$this->master=$master;
		
		$this->output="";
		$this->attrs=array();
		$this->ready=false;
		
		$this->addf=array();
		$this->delf=array();
		$this->logger=false;
	}
	/*! assign log master object
	
		Has not any default usage except of debug purposes
		@param logger 
			LogMaster object
		@return 
			description of return value
	*/
	function assign_logger($logger){
		$this->logger=$logger;
	}
	/*! log a record , if LogMaster was assigned
		
		@param a 
			string which will be logged
		@param b
			data which will be added to log record, optional
	*/
	function log($a,$b=""){
		if ($this->logger)
			$this->logger->log($a,$b);
	}
	
	/*! add custom field and value to DB operation
		
		@param name 
			name of field which will be added to DB operation
		@param value
			value which will be used for related field in DB operation
	*/
	function add_field($name,$value){
		$this->log("adding field: ".$name.", with value: ".$value);
		$this->data[$name]=$value;
		$this->addf[]=$name;
	}
	/*! remove field from DB operation
		
		@param name 
			name of field which will be removed from DB operation
	*/
	function remove_field($name){
		$this->log("removing field: ".$name);
		$this->delf[]=$name;
	}
	
	/*! sync field configuration with external object
		
		@param slave 
			SQLMaster object
		@todo 
			check , if all fields removed then cancel action
	*/
	function sync_config($slave){
		foreach ($this->addf as $k => $v)
			$slave->add_field($v);
		foreach ($this->delf as $k => $v)
			$slave->remove_field($v);
	}
	/*! get value of some record's propery
		
		@param name 
			name of record's property ( name of db field or alias )
		@return 
			value of related property
	*/
	function get_value($name){
		return $this->data[$name];
	}
	/*! set value of some record's propery
		
		@param name 
			name of record's property ( name of db field or alias )
		@param value
			value of related property
	*/
	function set_value($name,$value){
		$this->log("change value of: ".$name." as: ".$value);
		$this->data[$name]=$value;
	}
	/*! get hash of data properties
		
		@return 
			hash of data properties
	*/
	function get_data(){
		return $this->data;
	}
	/*! get some extra info attached to record
		
		Currently client side code encode all item related and global userdata so it can be accessed by this method. 
		@param name 
			name of userdata property
		@return 
			value of related userdata property
	*/
	function get_userdata_value($name){
		return $this->userdata[$name];
	}
	/*! set some extra info attached to record
		
		@param name 
			name of userdata property
		@param value
			value of userdata property
	*/
	function set_userdata_value($name,$value){
		$this->log("change userdata of: ".$name." as: ".$value);
		$this->userdata[$name]=$value;
	}
	/*! get current status of record
		
		@return 
			string with status value
	*/
	function get_status(){
		return $this->status;
	}
	/*! assign new status to the record
		
		@param status 
			new status value
	*/
	function set_status($status){
		$this->status=$status;
	}
	/*! get id of current record
		
		@return 
			id of record
	*/
	function get_id(){
		return $this->id;
	}
	/*! sets custom response text
		
		can be accessed through defineAction on client side. Text wrapped in CDATA, so no extra escaping necessary
		@param text 
			custom response text
	*/
	function set_response_text($text){
		$this->set_response_xml("<![CDATA[".$text."]]>");
	}
	/*! sets custom response xml
		
		can be accessed through defineAction on client side
		@param text
			string with XML data
	*/
	function set_response_xml($text){
		$this->output=$text;
	}
	/*! sets custom response attributes
		
		can be accessed through defineAction on client side
		@param name
			name of custom attribute
		@param value
			value of custom attribute
	*/
	function set_response_attribute($name,$value){
		$this->attrs[$name]=$value;
	}
	/*! check if action finished 
		
		@return 
			true if action finished, false otherwise
	*/
	function is_ready(){
		return $this->ready;
	}	
	/*! return new id value
	
		equal to original ID normally, after insert operation - value assigned for new DB record	
		@return 
			new id value
	*/
	function get_new_id(){
		return $this->nid;
	}
	/*! return id of master record
		
		Doens't have sense if record has not related master record
		@return 
			id of master record
	*/
	function master_id(){
		if ($this->master) return $this->master->get_new_id();
	}
	
	/*! set result of operation as error
	*/
	function error(){
		$this->status="error";
		$this->ready=true;
		if ($this->master)
			$this->master->error();
	}
	/*! set result of operation as invalid
	*/
	function invalid(){
		$this->status="invalid";
		$this->ready=true;
		if ($this->master)
			$this->master->invalid();
	}
	/*! confirm successful opeation execution
		@param  id
			new id value, optional
	*/
	function success($id=false){
		if ($id!==false)
			$this->nid = $id;
		$this->ready=true;
		if ($this->master)
			$this->master->success($id);
	}
	/*! convert DataAction to xml format compatible with client side dataProcessor
		@return 
			DataAction operation report as XML string
	*/
	function to_xml(){
		$str="<action type='{$this->status}' sid='{$this->id}' tid='{$this->nid}' ";
		foreach ($this->attrs as $k => $v) {
			$str.=$k."='".$v."' ";
		}
		$str.=">{$this->output}</action>";	
		return $str;
	}
	/*! convert self to string ( for logs )
		
		@return 
			DataAction operation report as plain string 
	*/
	function __toString(){
		return "action:{$this->status}; sid:{$this->id}; tid:{$this->nid};";
	}
	

}


?>