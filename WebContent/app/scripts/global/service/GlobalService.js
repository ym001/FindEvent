(function(){
	'use strict';
	var mod = angular.module('global.service');

	mod.factory('myHttpInterceptor', ['$q', '$window', '$rootScope', function ($q, $window, $rootScope) {
		return function (promise) {
			return promise.then(function (response, status) {
				// do something on success
				if(!_(response.config.url).endsWith("html")){
					$rootScope.$broadcast('spinnerOff');
					//console.log('spinnerOff with '+response.config.url);
				}
				return response;

			}, function (response) {
				// do something on error
				// todo hide the spinner
				var alertObj = {type:'error', msg:'Erreurs serveur de STATUS:'+response.status};
				$rootScope.$broadcast('alertOn', alertObj);
				$rootScope.$broadcast('spinnerOff');

				return $q.reject(response);
			});
		};
	}]);

	mod.config(['$httpProvider', function ($httpProvider) {
		$httpProvider.responseInterceptors.push('myHttpInterceptor');
	}]);


	//Navigation
	mod.constant('Navigation',
			[
			 {  when:"/login", 
				 templateUrl:"views/login.html", 
				 controller:"LoginCtrl"
			 }, 
			 {  when:"/accueil", 
				 templateUrl:"views/accueil.html", 
				 controller:"AccueilCtrl"
			 },
			 {  when:"/festivales", 
				 templateUrl:"views/festivale.html", 
				 controller:"FestivaleCtrl"
			 },
			 {  when:"/concerts", 
				 templateUrl:"views/concert.html", 
				 controller:"ConcertCtrl"
			 },
			 {  when:"/groupes", 
				 templateUrl:"views/groupe.html", 
				 controller:"GroupeCtrl"
			 }, 
			 {  when:"/nouveauCompte", 
				 templateUrl:"views/nouveau_compte.html", 
				 controller:"NouveauCompteCtrl"
			 }

			 ]);


	//web methodes Facade
	mod.factory("cmWSFacade", 
			['$http', 'serverRESTConfig','$rootScope', 
			 function($http, serverRESTConfig, $rootScope){

				var compo_url= serverRESTConfig.protocal+
				serverRESTConfig.domain+':'+
				serverRESTConfig.port+
				serverRESTConfig.context;


				return {
					cmWSGet:function(uri){
						$rootScope.$broadcast('spinnerOn');
						console.log('NOW GET '+compo_url+uri);
						return $http({
							url:compo_url+uri,
							method:"GET", 
							cache:false, 
							timeout:2000
						}); 
					}, 

					cmWSPost:function(uri, content){
						$rootScope.$broadcast('spinnerOn');
						return $http({
							url:compo_url+uri,
							method:"POST",
							cache:false,
							timeout:2000,
							data:content
						});
					}
				}
			}]);

})();