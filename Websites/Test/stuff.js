function myMap() {
  var myCenter = new google.maps.LatLng(41.663223, -91.534936);
  var marker = new google.maps.Marker({position: myCenter});
  var mapOptions = {
    center: myCenter,
    zoom: 17,
    mapTypeId: google.maps.MapTypeId.MAP
  }
  var map = new google.maps.Map(document.getElementById("map"), mapOptions);
  marker.setMap(map);
}
