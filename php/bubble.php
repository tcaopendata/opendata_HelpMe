<?php 

?>
<html>
<head>
	<title>空污回報地圖</title>

	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.0-beta.2/leaflet.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.0.0-beta.2/leaflet.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="bubble/MarkerCluster.css" />
	<link rel="stylesheet" href="bubble/MarkerCluster.Default.css" />
	<script src="bubble/leaflet.markercluster-src.js"></script>
	 <link rel="stylesheet" href=".\sourse\leaflet-routing-machine.css" />

<script src=".\sourse\leaflet-routing-machine.js"></script>
	
<style>
 #map{ 
    	height: 100%;

    }

#progress {
    display: none;
    position: absolute;
    z-index: 1000;
    left: 400px;
    top: 300px;
    width: 200px;
    height: 20px;
    margin-top: -20px;
    margin-left: -100px;
    background-color: #fff;
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 4px;
    padding: 2px;
}

#progress-bar {
    width: 0;
    height: 100%;
    background-color: #76A6FC;
    border-radius: 4px;
}

</style>
</head>

<body>
<?php
  $longitude=$_GET['longitude'];
  $latitude=$_GET['latitude'];
?>
	<div id="map"></div>
	<script type="text/javascript">

function myFunction() {
   
    var txt;
    var person = prompt("輸入內容", "");
    if (person == null || person == "") {
        txt = "沒有文字";
    } else {      
       document.location.href="respo.php?cont="+person+"&lat="+<?php echo($longitude) ?>+"&lon="+<?php echo($latitude) ?>;
    }
    

}
		<?php 
		echo "var map = L.map('map').setView([".$latitude.",".$longitude."],17);";
  		 echo " var placeholder = L.icon({iconUrl: 'placeholder.png',iconSize: [38, 38],});";
        echo " L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',{attribution: 'Data from <a href=\"http://data.kaohsiung.gov.tw/opendata/\">即時開放平台</a>|' +'Imagery©<a href=\"http://mapbox.com\">Mapbox</a>',maxZoom: 17,minZoom: 9,}).addTo(map);";
		?>

		var markers = L.markerClusterGroup();
		<?php 
		error_reporting(E_ALL ^ E_DEPRECATED);
		header("Content-Type:text/html; charset=utf-8");
		$link = mysql_connect('localhost:3306/phpmyadmin','root','vertrigo')or die('Cannot connect to the DB');
	    mysql_query("SET NAMES 'UTF8'");
	    mysql_select_db('map_test',$link) or die('Cannot select the DB');	
	    $query = "SELECT * FROM map";
	    $result = mysql_query($query,$link) or die('Errant query:  '.$query);
	    $no = 0;


	    while($row = mysql_fetch_array($result)) {
		$no++;
	   // $no = 0;
		echo "var title = '".$row['name']."';
		var marker = L.marker(new L.LatLng(".$row['latitude'].",".$row['longitude']."), {title: '".$row['name']."' });
		marker.bindPopup(title);markers.addLayer(marker);";
		}	

        ?>
		map.addLayer(markers);
		<?php
 		 echo "var me = L.icon({iconUrl:'person24.png',iconSize:[38, 38],});";
  		 echo 'var marker_me = L.marker(['.$latitude.','.$longitude.'],{icon:me}).addTo(map);';
		 echo 'marker_me.bindPopup("<strong>我在這</strong>").openPopup();';
		 echo "   var a = 0;
   var routing;
function onClick(e) {
    if (a == 0){
 routing = L.Routing.control({
    waypoints: [
    L.latLng(".$latitude.','.$longitude."),
    L.latLng(e.latlng.lat,e.latlng.lng)
  ]
}).addTo(map);
  a=1;
}

}";
 
		 // latitude 	longitude 	name
	?>

	 map.on('click', function(e) {    
   
if (a==1){
routing.spliceWaypoints(0, 2);
 var elements = document.getElementsByClassName("leaflet-routing-container");
    while(elements.length > 0){
        elements[0].parentNode.removeChild(elements[0]);
    }
   
//this.removeControl(routing);
// map.removeControl(routing);
//map.update();
a=0;
}
});
	 markers.addEventListener("click", function(e){
    onClick(e);
});
console.log("123");
	</script>
	
</body>
</html>
