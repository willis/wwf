<?php

require_once("dataprocessor.php");
/*! DataProcessor class for Calendar component
**/
class SchedulerDataProcessor extends DataProcessor{
	function get_post_values($ids){
		$data=array(); 
		for ($i=0; $i < sizeof($ids); $i++)
			$data[$i]=array();
		
		foreach ($_POST as $key => $value) {
			$details=explode("_",$key,2);
			if (sizeof($details)==1) continue;
			$data[$details[0]][$details[1]]=$value;
		}
		
		return $data;
	}
	function process($form=false){
		$this->logger->log("Edit operation started [Calendar]",$_POST);
		$results=array();

		
		$ids=explode(",",$_POST["ids"]);
		$rows_data=$this->get_post_values($ids);
		for ($i=0; $i < sizeof($ids); $i++) { 
			$rid = $ids[$i];
			$status = $_POST[$rid."_!nativeeditor_status"];
			$data = $this->get_post_values($rid);
			$this->logger->log("Event data [{$rid}]",$rows_data[$rid]);
			$action=$this->inner_process($status,$rid,$this->name_data($rows_data[$rid]));
			$results[]=$action;
		}
		
		$this->output_edit($results);
	}
	
	function name_data($data){
		$cf=$this->sql->config;

		$res = array();
		$res[$cf["field"][0][1]]=$data["start_date"];
		$res[$cf["field"][1][1]]=$data["end_date"];
		$res[$cf["field"][2][1]]=$data["text"];
			
		for ($i=0; $i < sizeof($cf["field"]); $i++)
			if (isset($data[$cf["field"][$i][1]]))
				$res[$cf["field"][$i][1]]=$data[$cf["field"][$i][1]];
			
		return array("data"=>$res,"original"=>$data);
	}
}
?>