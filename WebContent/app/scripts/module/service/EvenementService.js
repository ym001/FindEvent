(function(){
	'use strict';
	var mod = angular.module('mod.service');

	mod.factory('EvenementService', ['$rootScope', 'cmWSFacade', 'webStorage',
	                                 function($rootScope, cmWSFacade,webStorage){

		return{
			getAllEvenements:function(lat, lgt, distance){
				return cmWSFacade.cmWSGet('EvenementService/getAllEvenements?lat='+lat+'&lgt='+lgt+'&distance='+distance);
			}
		}

	}]);

})();