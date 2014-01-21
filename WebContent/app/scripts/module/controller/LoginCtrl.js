(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('LoginCtrl', ['$scope', '$rootScope', '$location', 'cmWSFacade', 'LoginService', '$http', 
	                                function($scope, $rootScope, $location, cmWSFacade, LoginService, $http){


		$scope.loadAddresseInfo = function(){
			$rootScope.$broadcast('spinnerOn');
			return $http({
				method:'JSONP', 
				url:"http://freegeoip.net/json/?callback=JSON_CALLBACK"
			});
		};
		
		
		$scope.maPosition = function(position){
			console.log(position);
		}
		
		$scope.checkLogin = function(){
			//function d'authentification
			LoginService.checkLogin($scope.nom, $scope.pass).success(function(data, status){
				if(data.result=='success'){
					//aussi associer style du musique
					$scope.type_musique = 'pop';
					
					$scope.$emit('info_user', {
						email:data.binding[0].email,
						nom : $scope.nom,
						pass : $scope.pass, 
						type_musique : data.binding[0].styles, 
						distance:20
					});
					
					//WS freeipgeo
					$scope.loadAddresseInfo().success(function(data, status){
						$rootScope.$broadcast('spinnerOff');
						$scope.$emit('info_geo', data);
						//redireger a la page d'accueil
						$location.path('/accueil');
					}).error(function(data, status){
						console.log(data);
						console.log(status);
						alert('ip_add serveur ne fonctionne pas!');
						//alert('on va choisir notre propre adresse');											
						if(navigator.geolocation){
							navigator.geolocation.getCurrentPosition($scope.maPosition);
						}
						//Mettre en statique si probleme de connextion
						$rootScope.$broadcast('spinnerOff');
						var geoData = {
							"ip":"162.38.218.204",
							"region_name":"Languedoc-Roussillon",
							"latitude":43.6109,
							"longitude":3.8772,
							"city":"Montpellier",
							"region_code":"A9",
							"country_code":"FR", 
							"country_name":"France"
						}
						$scope.$emit('info_geo', geoData);
						//redireger a la page d'accueil
						$location.path('/accueil');
						
					});

				}else{// fause d'authentification
					alert('Utilisateur not existe');
				}
			}).error(function(data, status){
				alert('Server error!');
			});
		}

	}]);

})();