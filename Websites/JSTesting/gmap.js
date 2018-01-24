function initMap() {
  var myCenter = new google.maps.LatLng(42, -93.0977);
  var mapOptions = {
    center: myCenter,
    zoom: 7,
    mapTypeId: google.maps.MapTypeId.MAP
  };
  var map = new google.maps.Map(document.getElementById("map"), mapOptions);
  var marker;
  var position;
  d3.csv('latlong.csv', function(data) {
    data.forEach(function (d) {
      position = new google.maps.LatLng(d.LATITUDE, d.LONGITUDE);
      marker = new google.maps.Marker({
        position: position,
        map: map,
        label: d.NAME
      });
    });
  });
}
