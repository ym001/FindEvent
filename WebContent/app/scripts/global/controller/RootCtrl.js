(function(){
	'use strict';
	var module = angular.module('global.controller');

	module.controller('RootCtrl', ['$scope', '$rootScope', 'webStorage', '$location',
	                               function($scope, $rootScope, webStorage, $location){

		//Par defaut:
		$scope.spinner = false;


		//Pour definir et assigner les variables globales

		//info_user
		$scope.$on('info_user', function(event, data){
			if(webStorage.session.get('$info_user')==null){
				webStorage.session.add('$info_user', data);
			}

			console.log(webStorage.session.get('$info_user'));
		});


		//info_geo
		$scope.$on('info_geo', function(event, data){

			if(_.isEmpty(data.city)){ //in case not found city
				data = {
						"ip":"162.38.218.204",
						"region_name":"Languedoc-Roussillon",
						"latitude":43.6109,
						"longitude":3.8772,
						"city":"Montpellier",
						"region_code":"A9",
						"country_code":"FR", 
						"country_name":"France"
				}
			}

			webStorage.session.add('$info_geo', data);
			console.log(webStorage.session.get('$info_geo'));
		});



		//La gestion de spinner
		$scope.$on('spinnerOn', function(event, data){
			$scope.spinner = true;
			console.log('spinnerON');
		});

		$scope.$on('spinnerOff', function(event, data){
			$scope.spinner = false;
			console.log('spinnerOFF');
		});


		//La gestion de session
		$scope.$on('$routeChangeStart', function(event, next, current){

			//Si cas nouveau client
			if(next.$$route.controller=='NouveauCompteCtrl'){
				//
			}else{
				//verifier si la session est toujours valide
				if(webStorage.session.get('$info_user')==null){
					//redigerer a la page login
					$location.path('/login');
				}
			}
		});

	}]);

})();