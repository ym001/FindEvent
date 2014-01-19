(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('ArtisteCtrl',  ['$scope', '$location', 'cmWSFacade', 'webStorage', 'EvenementService',
	                                   function($scope, $location, cmWSFacade, webStorage,EvenementService){

		$scope.$info_user = webStorage.session.get('$info_user');
		$scope.dataArtistes = [];


		$scope.gridOptions = {
				data: 'dataArtistes',
				i18n:'fr',
				rowHeight: 50,
				enableColumnResize:true, 
				enableRowReordering:false, 
				enableSorting:true,
				showColumnMenu:true, 
				showFilter:true,
				showSelectionCheckbox:false, 
				selectWithCheckboxOnly:false,
				editableCellTemplate: true, 
				//plugins:[new ngGridFlexibleHeightPlugin()],
				columnDefs: [
				             {field:'img', displayName:'Image', cellTemplate:'template/templateArtiste.html'}, 
				             {field:'name', displayName:'Nom du Artiste'}, 
				             {field:'homepage', displayName:'Homepage', cellTemplate:'<div><a href="{{row.entity.homepage}}">{{row.entity.homepage}}</a></div>'}, 
				             {field:'bio', displayName:'Biologie Artiste', cellTemplate:'<div><div ng-bind-html="{{row.entity.bio}}"/></div>'}, 
				             {field:'', displayName:'Albums', cellTemplate:'template/templateArtisteAlbum.html'}
				             ]
		}

		$scope.init = function(){
			EvenementService.getArtistesByGenre($scope.$info_user.type_musique).success(function(data, status){
				$scope.dataTemp = data.binding;

				//Supprimer le redundant de resultat
				var uniques = _.uniq($scope.dataTemp,function(item){
				    return item.idJamendo;
				});
				
				$scope.dataArtistes = uniques;

			}).error(function(data, status){
				console.log(data);
			});
		}


		//Methode init
		$scope.init();

	}]);

})();