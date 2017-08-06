<?php session_start(); ?>
<?php 
error_reporting(E_ALL ^ E_DEPRECATED);
echo "<H1>推播系統</H1>";
//申請的API金鑰

$json = file_get_contents('https://maps.googleapis.com/maps/api/geocode/json?latlng='.$_GET["lat"].','.$_GET["lng"].'&key=AIzaSyA44jT6_EEowhEdfGe0TXgy5Pqm2cFjmlY');
$obj = json_decode($json);
$list =$obj;
$s = 0;
$tes = "";
$str=$list->results[3]->address_components[1]->long_name;
echo $str;

$API_KEY = "AIzaSyB5jFUjbufF2eial0vqPsFvfwxPmegkfrs";
//已知的APP推播識別ID
//$APP_ID = "APA91bGZM0qNquQ7rEMpxo4AL7TIxY8t9RPhNMiN8sMlVT2Blpa9MaQrX4_-7wTE1sLgYl1CkKcc6U-qMGboJ8WDxWJRy6HpE0adPFh_rUYRIUwNszsi5lAzUldUYB_bhRyeij1PWLIU";
$APP_ID= "APA91bFX1EpfvmI4PMo68lm4u_yKXRNEOk2GVeE7F8eoczDhikc-PUakwF067u2Uh1DLVLdXE2BT7IkYlpdU6Ak5yyO1XDmftG2J_SnHFw_rdRks1ww0hEmAfyORip0X-5ERLloXfTSPG78ztMupgwPXWrE8orV-HQ";


 header("Content-Type:text/html; charset=utf-8");
  $jsonurl='http://opendata2.epa.gov.tw/AQX.json'; 
  $json = file_get_contents($jsonurl,0,null,null);  
  $json_output = json_decode($json, true);    
  $link = mysql_connect('localhost:3306/phpmyadmin','root','vertrigo')or die('Cannot connect to the DB');
 session_destroy(); 
  mysql_query("SET NAMES 'UTF8'");
  mysql_select_db('gcm',$link) or die('Cannot select the DB');
  $query = "SELECT * FROM test";
  $result = mysql_query($query,$link) or die('Errant query:  '.$query);

  while($row = mysql_fetch_array($result)) {
          
   
   //	echo $trend['SiteName']."PM2.5 =".$trend['PM2.5']."PublishTime: ".$trend["PublishTime"]."\n";
   
    
  
   // echo"<hr>高雄地區：<br>"; 
 //echo($row['no']);
   	
  
 
  
//echo json_encode($php_array);
//推播訊息
//if(!empty($_POST['message']))
//{
	//設定識別ID

	//$registatoin_ids = array( $row['no']);
//

   echo $row["no"]."<hr>";
    if($row["pot"] =$str){
   $registatoin_ids = array($row["no"]);
	//設定內容
   // $message = array("message" => $_POST['message']);	
	$url = 'https://android.googleapis.com/gcm/send';
	$json = array(
            'registration_ids' => $registatoin_ids,
            'data'             => array( 
            							'message' => '開啟app查看吧~',
                                        'title'  => '您附近可能有人需要幫助',
                                        )

        );	
	$headers = array(
            'Authorization: key=' . $API_KEY,
            'Content-Type: application/json'
        );		
	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_POST, true);
	curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);	//忽略SSL驗證
	curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($curl, CURLOPT_POSTFIELDS, json_encode($json));
	$result = curl_exec($curl);
	curl_close($curl);
    echo $result;
	echo "<hr>";
}
}

//}
?>
<html>
<head>
<meta content="7200" http-equiv="refresh">
</head>

<script>
<?php 	
?>
</script>
<body>
<!--
<form method="post" action="" id="form1">
		Title: <input id="title" name="title" type="text" placeholder="Message"><br>
		Message: <input id="message" name="message" type="text" placeholder="Message"><br>
	<button type="submit">Send</button>
</form>
-->
</body>
</html>