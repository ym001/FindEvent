(function(){
	'use strict';
	var mod = angular.module('mod.service');
	
	mod.factory('AccueilService', ['$rootScope', 'cmWSFacade', 'webStorage',
	                               function($rootScope, cmWSFacade,webStorage){
		
		return{
		
			getStylesByUserName:function(username){
				return cmWSFacade.cmWSGet('AccueilService/stylesByUserName?username='+username, false);
			}, 
			
			getStylesAvailableByUserName:function(username){
				return cmWSFacade.cmWSGet('AccueilService/stylesAvailableByUserName?username='+username, false);
			}, 
			
			addUserStyles:function(username, style_id){
				return cmWSFacade.cmWSGet('AccueilService/userStyles/add?username='+username+'&style_id='+style_id, false);
			},
			
			deleteUserStyles:function(username, style_id){
				return cmWSFacade.cmWSGet('AccueilService/userStyles/delete?username='+username+'&style_id='+style_id, false);
			}
		
		
		}
	
	}]);
	                            
})();