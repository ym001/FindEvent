(function(){
	'use strict';
	var module = angular.module('global.controller');

	module.controller('HeaderCtrl', ['$scope', 'cmWSFacade','webStorage', '$location',
	                                 function($scope, cmWSFacade, webStorage, $location){
		
	/*	$scope.nom_user = webStorage.session.get('$info_user').nom;
		$scope.ville_actu = webStorage.session.get('$info_geo').city + '   '
		+ webStorage.session.get('$info_geo').region_name;*/
		
		//Items menu
		$scope.items = [
		                "accueil",
		                "evenement",
		                "artiste",
		                "logout"
		              ];
		
		
		$scope.menuFunc = function(choice){
			if(choice=='logout'){
				webStorage.session.clear();
				$location.path('/login');
			}else if(choice=='accueil'){
				$location.path('/accueil');
			}else if(choice=='evenement'){
				$location.path('/festivales');
			}else if(choice=='artiste'){
				$location.path('/artistes');
			}
			
		};
		
		
		
	}]);

})();