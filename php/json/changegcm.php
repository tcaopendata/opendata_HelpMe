
<?php 
header("Content-Type:text/html; charset=utf-8");
	error_reporting(E_ALL ^ E_DEPRECATED);
	//$link = mysqli_connect('localhost:3306/phpmyadmin','root','vertrigo','gcm')or die('Cannot connect to the DB');	
	$no=$_GET['regId'];	
	$pot=$_GET['pot'];	
	$swith=$_GET['swith'];	
	$value=0;
	$link = mysql_connect('localhost:3306/phpmyadmin','root','vertrigo')or die('Cannot connect to the DB');
	mysql_query("SET NAMES 'UTF8'");
	mysql_select_db('gcm',$link) or die('Cannot select the DB');

	//$query = "SELECT * FROM test WHERE no = '$no'";
	$query = "SELECT * FROM test WHERE no = '$no'";
	$result = mysql_query($query,$link) or die('Errant query:  '.$query);

$a=0;

while($row = mysql_fetch_array($result)) {

 		 echo $row['no'];

$a=1;

}
if($a ==1){
 //  $link = mysqli_connect('localhost:3306/phpmyadmin','root','vertrigo','gcm')or die('Cannot connect to the DB');	
$links = mysql_connect('localhost:3306/phpmyadmin','root','vertrigo')or die('Cannot connect to the DB');
	mysql_query("SET NAMES 'UTF8'");
	mysql_select_db('gcm',$links) or die('Cannot select the DB');
  
	$no=$_GET['regId'];	
	$query2 = "UPDATE test SET ".'pot'." = '".$pot."', ".'swith'." = '".$swith."',".'value'." = '".$value."' WHERE no = '$no'";
	$results = mysql_query($query2,$links) or die('Errant query:  '.$query2);
	//header("Location:http://140.127.220.216:8080/lobby.php?uid=".$number."&minor=".$class); 
	 echo "已更改";
	

}
if($a ==0){
 //  $link = mysqli_connect('localhost:3306/phpmyadmin','root','vertrigo','gcm')or die('Cannot connect to the DB');	
$links = mysql_connect('localhost:3306/phpmyadmin','root','vertrigo')or die('Cannot connect to the DB');
	mysql_query("SET NAMES 'UTF8'");
	mysql_select_db('gcm',$links) or die('Cannot select the DB');
  
	$no=$_GET['regId'];
	$query2 = "INSERT INTO test(no,pot,swith,value)VALUES('".$no."','".$pot."','".$swith."','".$value."');";
	$results = mysql_query($query2,$links) or die('Errant query:  '.$query2);
	//header("Location:http://140.127.220.216:8080/lobby.php?uid=".$number."&minor=".$class); 
	 echo "已新增".$query2;
	exit;

}
?>
