(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('FestivaleCtrl', ['$scope', 'cmWSFacade', 'webStorage', 'EvenementService','BusinessService','$modal',
	                                    function($scope, cmWSFacade, webStorage,EvenementService, BusinessService, $modal){
		//Get position $info_geo
		$scope.longitude = webStorage.session.get('$info_geo').longitude; 
		$scope.latitude = webStorage.session.get('$info_geo').latitude; 
		$scope.city = webStorage.session.get('$info_geo').city; 
		$scope.$info_user = webStorage.session.get('$info_user');
		$scope.genre = "";

		$scope.dataEvenements = [];
		$scope.gridOptions = {
				data: 'dataEvenements',
				i18n:'fr',
				rowHeight: 50,
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
				             {field:'location.locName', displayName:'Nom Location', cellTemplate:'<span class="ngCellText">{{row.entity.location.locName}}</span>'}, 
				             {field:'location.address', displayName:'Adresse', cellTemplate:'<span class="ngCellText">{{row.entity.location.address}}</span>'}, 
				             {field:'', displayName:'Annotation', cellTemplate:'<div class="ngCellText acenter"><button class="btn btn-success" ng-click="open(row.entity)">Annoter</button></div>'}, 
				             ]
		}


		$scope.init = function(){
			//Get Liste evenements
			EvenementService.getAllEvenements($scope.latitude, $scope.longitude, $scope.$info_user.distance, $scope.city, $scope.genre).success(function(data, status){
				$scope.dataEvenements = data.binding;
				console.log($scope.dataEvenements);
			}).error(function(data, status){
				console.log('error in getAllEvenements');
				console.log(data);
				console.log(status);
			});
		}


		/**
		 * Partie Annotation Modal
		 */
		$scope.open = function(rowEntity){
			console.log('open');
			console.log(rowEntity);
			$scope.$info_user = webStorage.session.get('$info_user');
			var content = {
					tag : "",
					annotation : "",
					event : rowEntity.name, 
					date : new Date(),
					lat : $scope.latitude,
					lgt : $scope.longitude,
					nom : $scope.$info_user.nom,
					mail : $scope.$info_user.email	
			}

			var modalInstance = $modal.open({
				scope:$scope,
				templateUrl: 'template/templateAnnotationModal.html',
				controller: ModalInstanceCtrl,
				resolve: {
					event:function(){
						return rowEntity.name;
					}
			/*	annotation:function(){
						return $scope.annotation;
					},*/ 
				}
			});

			//Treat returned values
			modalInstance.result.then(function (result) {
				console.log('annoted with'+result);
				content.annotation = result.annotation;
				BusinessService.addAnnotation(content).success(function(data, status){
					if(data.result=='success'){
						console.log('success');
					}else{
						console.log('error');
					}
				}).error(function(data, status){
					console.log('error');
				});
			}, function () {
				console.log('Modal dismissed at: ' + new Date());
			});
		}

		//Init scope of Modal
		var ModalInstanceCtrl = function ($scope, $modalInstance, event) {
			$scope.eventName = event;
			$scope.annoter = function(annotation){
				var result = {
						annotation:annotation
				}
				$modalInstance.close(result);
			}
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
		};



		//Methode a initialiser
		$scope.init();

	}]);

})();