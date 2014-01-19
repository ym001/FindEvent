(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('NouveauCompteCtrl', ['$scope', 'cmWSFacade', '$modal', '$log', 
	                                        function($scope, cmWSFacade, $modal, $log){

		/*$scope.open = function(){
			console.log('open');
			var modalInstance = $modal.open({
				scope:$scope,
				templateUrl: 'template/templateAnnotationModal.html',
				controller: ModalInstanceCtrl,
				resolve: {
						annotation:function(){
						return $scope.annotation;
					}, 
				}
			});

			//Treat returned values
			modalInstance.result.then(function (result) {
				console.log(result);
			}, function () {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		// Please note that $modalInstance represents a modal window (instance) dependency.
		// It is not the same as the $modal service used above.
		
		//Init scope of Modal
		var ModalInstanceCtrl = function ($scope, $modalInstance) {
			$scope.annoter = function(annotation){
				var result = {
					annotation:annotation
				}
				$modalInstance.close(result);
			}
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
		};*/
		
	}]);

})();