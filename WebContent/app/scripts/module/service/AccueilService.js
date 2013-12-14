(function(){
	'use strict';
	var mod = angular.module('mod.service');
	
	mod.factory('AccueilService', ['$rootScope', 'cmWSFacade', 'webStorage',
	                               function($rootScope, cmWSFacade,webStorage){
		
		return{
			getAllStyles:function(){
				return cmWSFacade.cmWSGet('hello');
			},
		
			getStylesByUserName:function(username){
				return cmWSFacade.cmWSGet('AccueilService/stylesByUserName?username='+username);
			}, 
			
			getStylesAvailableByUserName:function(username){
				return cmWSFacade.cmWSGet('AccueilService/stylesAvailableByUserName?username='+username);
			}, 
			
			addUserStyles:function(username, style_id){
				return cmWSFacade.cmWSGet('AccueilService/userStyles/add?username='+username+'&style_id='+style_id);
			},
			
			deleteUserStyles:function(username, style_id){
				return cmWSFacade.cmWSGet('AccueilService/userStyles/delete?username='+username+'&style_id='+style_id);
			}
		
		
		}
	
	}]);
	                            
})();