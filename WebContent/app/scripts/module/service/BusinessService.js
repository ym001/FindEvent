(function(){
	'use strict';
	var mod = angular.module('mod.service');

	mod.factory('BusinessService', ['$rootScope', 'cmWSFacade', 'webStorage',
	                                function($rootScope, cmWSFacade,webStorage){

		return{

			addAnnotation:function(content){
				return cmWSFacade.cmWSPost('BusinessService/addAnnotation', content);
			}


		}

	}]);
})();