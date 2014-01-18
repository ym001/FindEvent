(function(){
	'use strict';
	var mod = angular.module('mod.service');
	
	mod.factory('LoginService', ['$rootScope', 'cmWSFacade', 
	                               function($rootScope, cmWSFacade){
		
		return{
			checkLogin:function(username, login){
				return cmWSFacade.cmWSGet('LoginService/loginCheck?username='+username+'&password='+login);
			}
		}
	
	}]);
	                            
})();