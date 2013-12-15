(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('FestivaleCtrl', ['$scope', 'cmWSFacade', 'webStorage', 'EvenementService',
	                                    function($scope, cmWSFacade, webStorage,EvenementService){

		//Get position $info_geo
		$scope.longitude = webStorage.session.get('$info_geo').longitude; 
		$scope.latitude = webStorage.session.get('$info_geo').latitude; 
		$scope.$info_user = webStorage.session.get('$info_user');

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
				columnDefs: [{field:'nom', displayName:'Nom Festival'}, 
				             {field:'date', displayName:'Date'},
				             {field:'genre', displayName:'Genre de musique'}, 
				             {field:'participant', displayName:'Artists Participants'},
				             {field:'wikilink', displayName:'WikiLink', cellTemplate:'template/templateEvenement.html'}, 
				             ]
		}



		$scope.init = function(){
			//Get Liste styles from user
			EvenementService.getAllEvenements($scope.latitude, $scope.longitude, $scope.$info_user.distance).success(function(data, status){
				$scope.dataEvenements = data.binding;
				console.log($scope.dataEvenements);
			});
		}


		//Methode a initialiser
		$scope.init();




	}]);

})();