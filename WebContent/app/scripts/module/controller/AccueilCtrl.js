(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('AccueilCtrl', ['$scope', '$location', 'webStorage', 'AccueilService', 'EvenementService',
	                                  function($scope, $location, webStorage, AccueilService, EvenementService){

		//Get position $info_geo
		$scope.longitude = webStorage.session.get('$info_geo').longitude; 
		$scope.latitude = webStorage.session.get('$info_geo').latitude; 
		$scope.$info_user = webStorage.session.get('$info_user');

		$scope.nom_user = webStorage.session.get('$info_user').nom;
		$scope.ville_actu = webStorage.session.get('$info_geo').city + '   '
		+ webStorage.session.get('$info_geo').region_name;
		$scope.distance = webStorage.session.get('$info_user').distance;


		/**
		 * config google Maps
		 */
		$scope.map={
				center:{
					latitude: $scope.latitude,
					longitude: $scope.longitude
				},
				zoom:8, 
				draggable:true
		}

		$scope.onMarkerClicked = function (marker) {
			console.log('on marker clicked');
	        marker.showWindow = true;
	        //window.alert("Marker: lat: " + marker.latitude + ", lon: " + marker.longitude + " clicked!!")
	    };


		/**
		 * Angular Functions
		 */
		$scope.addUserStyles = function(style_id){
			AccueilService.addUserStyles($scope.$info_user.nom, style_id).success(function(data, status){
				$scope.stylesByUser = data.binding;
			});
		}

		$scope.deleteUserStyles = function(style_id){
			AccueilService.deleteUserStyles($scope.$info_user.nom, style_id).success(function(data, status){
				$scope.stylesByUser = data.binding;
			});
		}


		$scope.getAvailables = function(){
			//Get Liste des styles non preferes par users
			AccueilService.getStylesAvailableByUserName($scope.$info_user.nom).success(function(data, status){
				$scope.stylesReste = data.binding;
			});
		}

		$scope.meLocaliser = function(){
			//Markers
			$scope.eventmarkers = [];
			$scope.myPositionMarker ={
					coords:{
						latitude: $scope.latitude,
						longitude: $scope.longitude
					}, 
					showWindow:true
			};

			//Centralize map
			$scope.map.center= {
					latitude:$scope.latitude, 
					longitude:$scope.longitude
			};
		}

		$scope.getAllEvenementsSurMap = function(){
			$scope.city = webStorage.session.get('$info_geo').city; 
			EvenementService.getAllEvenements($scope.latitude, $scope.longitude, $scope.$info_user.distance, $scope.city, $scope.genre).success(function(data, status){
				var allLocations = _.pluck(data.binding, 'location');//For googlemap markers
				_.each(allLocations, function(item, index){
					item.name = data.binding[index].name; //name of event
					var toPut = {
							showWindow:false,
							coords:item, 
							icon:'images/lat_small.png', 
							closeClick:function(){
								item.showWindow = false;
								$scope.$apply();
							}
					}
					$scope.eventMarkers.push(toPut);
				});
				console.log($scope.eventMarkers);
			});
		}

		$scope.afficherGroupesByGenre = function(){
			//les types musiques sont deja configure ds session
			$location.path('/groupes');
		}

		$scope.afficherArtistesByGenre = function(){
			//les types musiques sont deja configure ds session
			$location.path('/artistes');
		}

		$scope.afficherEvenementsEnDetaille = function(){
			$scope.$info_user.distance = $scope.distance;
			webStorage.session.add('$info_user', $scope.$info_user);
			$location.path('/festivales');
		}


		$scope.$watch('stylesByUser', function(){
			$scope.$info_user.type_musique = $scope.stylesByUser;
			webStorage.session.add('$info_user', $scope.$info_user);
		}, true);


		$scope.$watch('distance', function(){
			$scope.$info_user.distance = $scope.distance;
			webStorage.session.add('$info_user', $scope.distance);
		}, true)


		$scope.init = function(){
			//Get Liste styles from user
			AccueilService.getStylesByUserName($scope.$info_user.nom).success(function(data, status){
				$scope.stylesByUser = data.binding;
				$scope.$info_user.type_musique = $scope.stylesByUser;
				webStorage.session.add('$info_user', $scope.$info_user);
			});


			//init Maps Markers
			$scope.myPositionMarker = {
					coords:{
						latitude: $scope.latitude,
						longitude: $scope.longitude
					}, 
					showWindow:true
			}

			$scope.eventMarkers = [];
		}

		//Methodes a initialiser
		$scope.init();


	}]);

})();