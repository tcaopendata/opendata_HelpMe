<?php
$addr_latlng_array = array(); //用來存抓到的經緯度
$dbname="hopinew.csv";  //開啟
$file = fopen($dbname,"r");
$no = 0;
while(!feof($file))
  {  

    set_time_limit(10);
    $str =explode(',',fgets($file));
    $addr_str = $str[2];
    if($addr_str!=""){
	$addr_str_encode = urlencode($addr_str);
	$url = "http://maps.googleapis.com/maps/api/geocode/json"."?sensor=true&language=zh-TW&region=tw&address=".$addr_str_encode;
	$geo = file_get_contents($url);
	$geo = json_decode($geo,true);
	$geo_status = $geo['status'];
	//echo "$addr_str $geo_status\n";
	if($geo_status=="OVER_QUERY_LIMIT"){ die("OVER_QUERY_LIMIT"); }
	if($geo_status!="OK") continue;
	$geo_address = $geo['results'][0]['formatted_address'];
	$num_components = count($geo['results'][0]['address_components']);
	//郵遞區號、經緯度
	$geo_zip = $geo['results'][0]['address_components'][$num_components-1]['long_name'];
	$geo_lat = $geo['results'][0]['geometry']['location']['lat'];
	$geo_lng = $geo['results'][0]['geometry']['location']['lng'];
	$geo_location_type = $geo['results'][0]['geometry']['location_type'];
	echo $str[0].','.$str[1].','.$str[2].",".$geo_lat.",".$geo_lng."<br>";
	sleep(2);
}
 
   	sleep(2);
 
}


?>