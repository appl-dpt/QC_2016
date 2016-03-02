var map;
var dataFromServer;
var markers = [];

function loadMapModerator(latitude, longitude, myZoom) {
		var latlng = new google.maps.LatLng(latitude, longitude);
		var myOptions = {
			zoom : myZoom,
			center : latlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map($("#map_container_moderator")[0], myOptions);
	}

function placeMarker(apartment, icon) {
	var latlng = new google.maps.LatLng(apartment.latitude, apartment.longitude);
    var marker = new google.maps.Marker({
        position: latlng, 
        map: map,
        title: apartment.name,
        icon: icon
    });
    
    marker.addListener('click', function() {
    	infowindow = new google.maps.InfoWindow({
            content: "<a href='" + $("#path").val() + "moderator/preview/"+apartment.id+"'>" 
			+apartment.name+ "</a>"
          });
        infowindow.open(map, marker);
      });
    markers.push(marker); 
    
}

function read(data) {
	var txtJSON = JSON.stringify(data, null, 4);
	dataFromServer = JSON.parse(txtJSON);
	loadMapModerator(dataFromServer.latitude, dataFromServer.longitude, 12);
	placeApartments();
}

function clearMarkers() {
	for (var i = 0; i < markers.length; i++) {
	    markers[i].setMap(null);
	}
	markers = [];
} 

function placeApartments() {
	if ($("#filterMarkers").val() == 'myApartments' || $("#filterMarkers").val() == 'allApartments') {
		dataFromServer.myApartments.forEach(function(item, i, arr) {
			placeMarker(item, $("#path").val() + 'img/myApartments.png');
		});
	}
	if ($("#filterMarkers").val() == 'freeApartments' || $("#filterMarkers").val() == 'allApartments') {
		dataFromServer.freeApartments.forEach(function(item, i, arr) {
			placeMarker(item, $("#path").val() + 'img/freeApartments.png');
		});
	}
	if ($("#filterMarkers").val() == 'otherApartments' || $("#filterMarkers").val() == 'allApartments') {
		dataFromServer.otherApartments.forEach(function(item, i, arr) {
			placeMarker(item, $("#path").val() + 'img/otherApartments.png');
		});
	}
}

$(document).ready(function() {
	getApartmentsLocation();
	
	$("#filterMarkers" ).change(function() {
		clearMarkers();
		placeApartments();
	});
});
