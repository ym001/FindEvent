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
			if(webStorage.session.get('$info_geo')==null){
				webStorage.session.add('$info_geo', data);
			}

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