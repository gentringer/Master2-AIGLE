var map;
var vectorLayerJSON;
var style_green;
var epsg4326;
var epsg900913;
var options;
var osm;
var selector


  

 function onPopupClose(evt) {
            selectControl.unselect(selectedFeature);
        }
        function onFeatureSelect(feature) {
            selectedFeature = feature;
            popup = new OpenLayers.Popup.FramedCloud("popup", 

                                     feature.geometry.getBounds().getCenterLonLat(),
                                     new OpenLayers.Size(100,100),
                                     "<div style='font-size:12'>"+feature.attributes.name+"</div>",

                                     null, true, onPopupClose);
            feature.popup = popup;
            map.addPopup(popup);
        }
        function onFeatureUnselect(feature) {
            map.removePopup(feature.popup);
            feature.popup.destroy();
            feature.popup = null;
        }



function init() {


 map = new OpenLayers.Map("map", options);



     var pulsate = function(feature) {
	var point = feature.geometry.getCentroid(),
        bounds = feature.geometry.getBounds(),
        radius = Math.abs((bounds.right - bounds.left)/2),
        count = 0,
        grow = 'up';

	var resize = function(){
            if (count>16) {
		clearInterval(window.resizeInterval);
            }
            var interval = radius * 0.03;
            var ratio = interval/radius;
            switch(count) {
            case 4:
            case 12:
                grow = 'down'; break;
            case 8:
                grow = 'up'; break;
            }
            if (grow!=='up') {
		ratio = - Math.abs(ratio);
            }
            feature.geometry.resize(1+ratio, point);
            vector.drawFeature(feature);
            count++;
	};
	window.resizeInterval = window.setInterval(resize, 50, point, radius);
    };

    var geolocate = new OpenLayers.Control.Geolocate({
	bind: false,
	geolocationOptions: {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: 7000
	}
    });
    map.addControl(geolocate);

    var circle;
    var firstGeolocation = true;
    geolocate.events.register("locationupdated",geolocate,function(e) {
	vector.removeAllFeatures();
	localx = e.position.coords.longitude;
	localy = e.position.coords.latitude;
	circle = new OpenLayers.Feature.Vector(
            OpenLayers.Geometry.Polygon.createRegularPolygon(
		new OpenLayers.Geometry.Point(e.point.x, e.point.y),
		e.position.coords.accuracy/2,
		40,
		0
            ),
            {},
            stylelocate
	);
	vector.addFeatures([
            new OpenLayers.Feature.Vector(
		e.point,
		{},
		{
                    graphicName: 'cross',
                    strokeColor: '#f00',
                    strokeWidth: 2,
                    fillOpacity: 0,
                    pointRadius: 10
		}
            ),
            circle
	]);
	
    });
    geolocate.events.register("locationfailed",this,function() {
	alert('Location detection failed');
    });
    //geolocate.activate();

	    map.addControl(new OpenLayers.Control.MousePosition({displayProjection: epsg4326})); //affichage localisation de la souris
    
    var gsat = new OpenLayers.Layer.Google(
	"Google Satellite",
	{type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22,
	 sphericalMercator: true, visibility: false}
    );
     var gphy = new OpenLayers.Layer.Google(
    "Google Physical",
    {type: google.maps.MapTypeId.TERRAIN}
    );
    
    var ghyb = new OpenLayers.Layer.Google(
    "Google Hybrid",
    {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
    // used to be {type: G_HYBRID_MAP, numZoomLevels: 20}
    );

    osm = new OpenLayers.Layer.OSM();
    gmap = new OpenLayers.Layer.Google("Google Streets", {type: google.maps.MapTypeId.STREETS, numZoomLevels: 22,
	 sphericalMercator: true, visibility: false} );
  
    // note that first layer must be visible
        map.addLayers([gphy,osm,gsat,gmap,ghyb,vectorLayerJSON]);
    map.setBaseLayer(map.layers[0]);

     map.addControl(new OpenLayers.Control.MousePosition({displayProjection: epsg4326})); //affichage localisation de la souris
    var mapBounds = new OpenLayers.Bounds(3.77,43.65,3.97,43.62).transform(epsg4326,epsg900913);

    map.zoomToMaxExtent();

    map.addControl(new OpenLayers.Control.Navigation());
    map.addControl(new OpenLayers.Control.PanZoomBar({zoomWorldIcon: true}));
    map.addControl(new OpenLayers.Control.OverviewMap({autoPan : true}));
    map.addControl(new OpenLayers.Control.KeyboardDefaults()); //se déplacer avec le clavier
    map.addControl(new OpenLayers.Control.ScaleLine()); //barre d'échelle
    map.addControl(new OpenLayers.Control.LayerSwitcher());

//    var point =  new OpenLayers.Geometry.Point(3.4, 43.3).transform(epsg4326, epsg900913);
//
//	var pointFeature = new OpenLayers.Feature.Vector(point, style_green);
//	vectorLayerJSON.addFeatures([pointFeature]);
    selectControl = new OpenLayers.Control.SelectFeature([vectorLayerJSON],
                         {onSelect: onFeatureSelect, onUnselect: onFeatureUnselect});
map.addControl(selectControl);
selectControl.activate();  
	drawmap();
}


function registerclick(){
        map.events.register('click', map, handleMapClick);

}

function unregisterclick(){

        map.events.unregister('click', map, handleMapClick);

}


var geocoder;

function handleMapClick(e)
{
   var lonlat = map.getLonLatFromViewPortPx(e.xy);
   // use lonlat

   // If you are using OpenStreetMap (etc) tiles and want to convert back 
   // to gps coords add the following line :-
    lonlat.transform( map.projection,map.displayProjection);

   var Longitude = lonlat.lon;
   var Latitude  = lonlat.lat;
             var barlon = document.getElementById ("barlon");
             barlon.value = Latitude;
             var barlat = document.getElementById ("barlat");
             barlat.value=Longitude;

  geocoder = new google.maps.Geocoder();

   var latlng = new google.maps.LatLng(Latitude,Longitude);



    codeLatLng(latlng);

    unregisterclick();

} 

function codeLatLng(latlng) {


  geocoder.geocode({'latLng': latlng}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      if (results[0]) {
        
          var input = document.getElementById ("needplaceholder");
            input.value = results[0].formatted_address;
ShowImageInModal();

      } else {
        alert('No results found');
      }
    } else {
      alert('Geocoder failed due to: ' + status);
    }
  });
}

 function ShowImageInModal() {
            jQuery.noConflict();
            (function ($) {
                $('#modalAdd').modal('show');
            }
            )(jQuery);

        }

function initbaselayer(){
	

    vectorLayerJSON = new OpenLayers.Layer.Vector("JSON Vector", {
    // 	isBaseLayer : false,
    // 	 eventListeners:{
    //     'featureselected':function(evt){
    //         var feature = evt.feature;
    //         var popup = new OpenLayers.Popup.FramedCloud("popup",
    //             OpenLayers.LonLat.fromString(feature.geometry.toShortString()),
    //             null,
    // "<div style='font-size:.8em'>Feature: " + feature.attributes.x +"<br>Foo: </div>",                null,
    //             true
    //                         );
    //         popup.autoSize = true;
    //         popup.maxSize = new OpenLayers.Size(400,800);
    //         popup.fixedRelativePosition = true;
    //         feature.popup = popup;
    //         map.addPopup(popup);
    //     },
    //     'featureunselected':function(evt){
    //         var feature = evt.feature;
    //         map.removePopup(feature.popup);
    //         feature.popup.destroy();
    //         feature.popup = null;
    //     }
    // }

        });
}

function createpoint(atr, pointf){
	
	var pointFeature = new OpenLayers.Feature.Vector(pointf, atr, style_green);
	vectorLayerJSON.addFeatures([pointFeature]);
	
}

function drawmap(){
	map.addLayer(vectorLayerJSON);
	map.zoomToExtent(vectorLayerJSON.getDataExtent());


}

function initvar(){
	  style_green = {
	            strokeColor: "#00FF00",
	            strokeWidth: 0,
	            pointRadius: 10,
	            pointerEvents: "visiblePainted",
	            externalGraphic: "assets/images/beer2.png"
	        };
	 epsg4326 = new OpenLayers.Projection('EPSG:4326');
	    epsg900913 = new OpenLayers.Projection('EPSG:900913');
	    
	    
	    options  = {
				controls: [],
				hover: false,
				projection: epsg900913,
				displayProjection: epsg4326,
				units : "m",
				extent: [-5, 35, 15, 55],
				maxResolution: 156543.0339,
				maxExtent: new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508.34)
			    };


}
