(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('AlbumCtrl',  ['$scope', 'cmWSFacade', 'webStorage', 'EvenementService','$routeParams',
	                                 function($scope, cmWSFacade, webStorage,EvenementService, $routeParams){

		$scope.$info_user = webStorage.session.get('$info_user');
		$scope.idArtiste = $routeParams.idArtiste;
		$scope.dataAlbums = [];


		$scope.gridOptions = {
				data: 'dataAlbums',
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
				             {field:'imgAlbum', displayName:'Image', cellTemplate:'template/templateAlbum.html'}, 
				             {field:'title', displayName:'Titre Album'}, 
				             {field:'datedc', displayName:'Date Création'}, 
				             {field:'lienTelecharge', displayName:'Téléchargement', 
				            	 cellTemplate:'<div class="acenter"><a href="{{row.entity.lienTelecharge}}"><i style="color:white; margin-top:25px" class="fa fa-2x fa-download"></i></a></div>'}, 
				             {field:'tag', displayName:'Genre'}
				            ]
		}

		$scope.init = function(){
			EvenementService.getAlbumsByArtiste($scope.idArtiste).success(function(data, status){
				$scope.dataTemp = data.binding;

				//Supprimer le redundant de resultat
				var uniques = _.uniq($scope.dataTemp,function(item){
					return item.idJamendo;
				});

				$scope.dataAlbums = uniques;

			}).error(function(data, status){
				console.log(data);
			});
		}


		//Methode init
		$scope.init();

	}]);

})();