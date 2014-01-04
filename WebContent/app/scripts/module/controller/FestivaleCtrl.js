(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('FestivaleCtrl', ['$scope', 'cmWSFacade', 'webStorage', 'EvenementService',
	                                    function($scope, cmWSFacade, webStorage,EvenementService){

		//Get position $info_geo
		$scope.longitude = webStorage.session.get('$info_geo').longitude; 
		$scope.latitude = webStorage.session.get('$info_geo').latitude; 
		$scope.city = webStorage.session.get('$info_geo').region_name; 
		$scope.$info_user = webStorage.session.get('$info_user');
		$scope.genre = "";

		$scope.dataEvenements = [];
		$scope.gridOptions = {
				data: 'dataEvenements',
				i18n:'fr',
				enableColumnResize:true, 
				enableRowReordering:false, 
				enableSorting:true,
				showColumnMenu:true, 
				showFilter:true,
				showSelectionCheckbox:false, 
				selectWithCheckboxOnly:false,
				//plugins:[new ngGridFlexibleHeightPlugin()],
				columnDefs: [{field:'name', displayName:'Nom Festival'}, 
				             {field:'date', displayName:'Date'},
				             //{field:'description', displayName:'Description'}, 
				             {field:'performers', displayName:'Artists Participants', cellTemplate:'<span class="ngCellText">{{row.entity.performers[0]}}</span>'},
				             {field:'location', displayName:'Nom Location', cellTemplate:'<span class="ngCellText">{{row.entity.location.locName}}</span>'}, 
				             {field:'location', displayName:'Adresse', cellTemplate:'<span class="ngCellText">{{row.entity.location.address}}</span>'}, 
				             ]
		}



		$scope.init = function(){
			//Get Liste styles from user
			EvenementService.getAllEvenements($scope.latitude, $scope.longitude, $scope.$info_user.distance, $scope.city, $scope.genre).success(function(data, status){
				$scope.dataEvenements = data.binding;
				console.log($scope.dataEvenements);
			});
		}

		//Methode a initialiser
		$scope.init();
		
	}]);

})();