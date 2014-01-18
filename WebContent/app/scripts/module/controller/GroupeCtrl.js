(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('GroupeCtrl',  ['$scope', 'cmWSFacade', 'webStorage', 'EvenementService',
	                                  function($scope, cmWSFacade, webStorage,EvenementService){
		
		$scope.$info_user = webStorage.session.get('$info_user');
		$scope.dataGroupes = [];
		
		
		$scope.gridOptions = {
				data: 'dataGroupes',
				i18n:'fr',
				enableColumnResize:true, 
				enableRowReordering:false, 
				enableSorting:true,
				showColumnMenu:true, 
				showFilter:true,
				showSelectionCheckbox:false, 
				selectWithCheckboxOnly:false,
				//plugins:[new ngGridFlexibleHeightPlugin()],
				columnDefs: [{field:'name', displayName:'Nom du Groupe'}, 
				             {field:'nick', displayName:'Nickname du Groupe'},
				             {field:'homepage', displayName:'Homepage'}, 
				             {field:'genre', displayName:'Genre de musique'},
				             {field:'seeAlso', displayName:'Link externe', cellTemplate:'template/templateGroupe.html'}, 
				             ]
		}
		
		
		$scope.init = function(){
			EvenementService.getGroupesByGenre($scope.$info_user.type_musique).success(function(data, status){
				$scope.dataGroupes = data.binding;
				console.log($scope.dataGroupes);
			});
		}
		
		
		//Methode init
		$scope.init();
		
	}]);

})();