(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('BusinessCtrl',  ['$scope', 'cmWSFacade', 'webStorage', 'BusinessService','$routeParams',
	                                    function($scope, cmWSFacade, webStorage,BusinessService, $routeParams){

		$scope.dataAnnotation = [];


		$scope.gridOptions = {
				data: 'dataAnnotation',
				i18n:'fr',
				rowHeight: 80,
				enableColumnResize:true, 
				enableRowReordering:false, 
				enableSorting:true,
				showColumnMenu:true, 
				showFilter:true,
				showSelectionCheckbox:false, 
				selectWithCheckboxOnly:false,
				//plugins:[new ngGridFlexibleHeightPlugin()],
				columnDefs: [
				             {field:'numAnno', displayName:'Numero Annotation'},
				             {field:'annotation', displayName:'Annotation'}, 
				             {field:'event', displayName:'Evenement'}, 
				             {field:'date', displayName:'Date creation'}, 
				             {field:'lat', displayName:'Latitude'}, 
				             {field:'lgt', displayName:'longitude'}, 
				             {field:'nom', displayName:'Nom'}, 
				             {field:'mail', displayName:'contact Email'}
				             ]
		}

		$scope.init = function(){
			BusinessService.getSampleBusiness().success(function(data, status){
				$scope.dataAnnotation = data.binding;
			}).error(function(data, status){
				console.log(data);
			});
		};

		//Methode init
		$scope.init();

	}]);

})();