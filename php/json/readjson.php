<?php  
if($_GET['type0']==2){
	//echo($_GET['serc']);
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
	$tes .=$value->wgs84aX.",";	
	$tes .=$value->wgs84aY;
	$tes .=">";
}
//print_r($value->name);
	}
echo $tes;

	if($tes==""){
		echo "查無資訊 ";
	}
}
else{
$row = 1;
if($_GET['type1']==1){
	$sr = "幼兒";
}
if($_GET['type1']==2){
	$sr = "老";
}
if($_GET['type1']==3){
	$sr="身心障礙";
}
if (($handle = fopen("./1/1.csv", "r")) !== FALSE) {
    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
        $num = count($data);

        
        $row++;
        if(strpos($data[0],$sr)){
        for ($c=0; $c < $num; $c++) {
            //echo $data[0] . "\r\n";
        }
         echo $data[0] . "\r\n \r\n".$data[1];
        echo">";
    }
    }
    fclose($handle);

}
}
?>