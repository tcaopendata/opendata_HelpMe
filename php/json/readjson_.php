<?php  
	//echo($_GET['serc']);
$json = file_get_contents('https://maps.googleapis.com/maps/api/geocode/json?latlng=24.969780,%20121.537684&key=AIzaSyA44jT6_EEowhEdfGe0TXgy5Pqm2cFjmlY');
$obj = json_decode($json);
$list =$obj;
$s = 0;
$tes = "";
//$str=$list->results[3]->address_components[1]->long_name;
echo $str;
?>