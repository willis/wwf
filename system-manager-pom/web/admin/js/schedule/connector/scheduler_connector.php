<?php
require_once("base_connector.php");
require_once("scheduler_dataprocessor.php");

//require_once("calendar_dataprocessor.php");
/*! DataItem class for Calendar component
**/
class SchedulerDataItem extends DataItem{
	/*! return self as XML string
	*/
	function to_xml(){
		if ($this->skip) return "";
		
		$str="<event id='".$this->id."' >";
		
		$str.="<start_date><![CDATA[".$this->data[$this->name[0][1]]."]]></start_date>";
		$str.="<end_date><![CDATA[".$this->data[$this->name[1][1]]."]]></end_date>";
		$str.="<text><![CDATA[".$this->data[$this->name[2][1]]."]]></text>";
		for ($i=3; $i<sizeof($this->name); $i++)
			$str.="<".$this->name[$i][1]."><![CDATA[".$this->data[$this->name[$i][1]]."]]></".$this->name[$i][1].">";
		return $str."</event>";
	}
}


/*! Connector class for Calendar component
**/
class SchedulerConnector extends Connector{
	function __construct($res,$type="MySQL"){
		parent::__construct($res,$type);
	}

	//parse GET scoope, all operations with incoming request must be done here
	function parse_request(){
		$this->editing = isset($_GET["editing"]);

		if (isset($_GET["from"])){
			$this->config["rules"]=array();
			//start date
			$this->config["rules"][]=array($this->config["field"][0][0],$this->db->escape($_GET["to"]),"<");
			//end date
			$this->config["rules"][]=array($this->config["field"][1][0],$this->db->escape($_GET["from"]),">");
		}
	}
	function render_table($table,$id,$field){
		if (!$id) $id="dhx_auto_generated_id";
		
		$this->set_config("field",$field,true);
		$this->set_config("id",$id);
		$this->set_config("table",$table);
		
		$this->event->trigger("beforeParse","");
		$this->parse_request();
		
		if ($this->editing){
			$this->sql->config($this->config);
			$dp = new SchedulerDataProcessor($this->db,$this->_log,$this->event,$this->sql,$this->access);
			$dp->process($this->form);
			$this->end_run();
		}		
		
						
		$result=$this->event->trigger("beforeFetch",$this->config);
		if ($result===true){
			$this->fill_query();
			$this->render();
		} else 
			$this->output=$result;
		
		$this->output_xml();
	}
		
	
	function render(){
		$this->render_set($this->db->query($this->query));
	}
	
	function render_sql($sql,$id,$field){
		$this->get_query($sql);
		$this->render_table($this->query->table,$id,$field);
	}
		
	function render_set($res){
		if (!$res)
			return $this->output_error();
			
		$id = $this->config["id"];
		$field = $this->config["field"];		
		$index=0;
		
		while ($data = $this->db->get_data_named($res)){
				$data = new SchedulerDataItem($data[$id[1]],$data,$field,$index++);
				$this->event->trigger("beforeRender",$data);
				$this->output.=$data->to_xml();
		}
	}

	function output_xml(){
		$this->output_header();
		echo "<data>";
		echo $this->output;
		echo "</data>";
		
		$this->end_run();
	}
}
?>