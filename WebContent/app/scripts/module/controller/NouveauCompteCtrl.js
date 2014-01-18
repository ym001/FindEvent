(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('NouveauCompteCtrl', ['$scope', 'cmWSFacade', function($scope, cmWSFacade){
		
		console.log($scope.nom);
		
		
	}]);

})();