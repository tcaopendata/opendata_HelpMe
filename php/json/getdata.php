<?php  
if($_GET['type0']==2){
	echo($_GET['serc']);
$json = file_get_contents('http://140.127.220.216:8080/json/2/'.$_GET['type1'].'/'.$_GET['type2'].'.json');
$obj = json_decode($json);
$list =$obj;
$s = 0;
$tes = "";
foreach ( $list  as $value ){
	$s++;
	if (strpos($value->address, $_GET['area']) !== false) {
    //echo 'true';
    $tes .= $value->title."\r\n";
	$tes .= "電話：".$value->tel."\r\n";
	$tes .= $value->address."\r\n ";	
	//echo $value->wgs84aX."/r";	
	//echo $value->wgs84aY;
	$tes .=">";
}
//print_r($value->name);
	}
echo $tes;

	if($tes==""){
		echo "查無資訊 ";
	}
}
?>