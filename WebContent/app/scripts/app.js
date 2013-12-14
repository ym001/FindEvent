(function () {
	'use strict';

	//permet d'utiliser underscore.string avec l'api d'underscore.js
	_.mixin(_.string.exports());

	var modules = [
	               { nomModule: 'global', deps: [] },
	               { nomModule: 'mod', deps: [] }
	              ];


	_.each(modules, function(mod){
		var
		filter = mod.nomModule + ".filter",
		directive = mod.nomModule + ".directive",
		controller = mod.nomModule + ".controller",
		service = mod.nomModule + ".service";

		angular.module(filter, []);
		angular.module(directive, []);
		angular.module(controller, []);
		angular.module(service, []);
		mod.deps = _.flatten([filter, directive, controller, service]);
	});  

	var appDeps = ['ngCookies', 'ngGrid', 'ui.directives', 
	               'ui.bootstrap', 'global.filter', 'webStorageModule', 'google-maps'];

	_.each(modules, function(mod){
		appDeps.push(mod.deps);
		appDeps = _.flatten(appDeps);
	}); 

	var app = angular.module('App', appDeps);

	//??
	app.config(['$httpProvider', function ($httpProvider) {
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
	}]);



	//Configure route provider, route controller and template url
	app.config(['$routeProvider','Navigation', function($routeProvider, Navigation) {
		var listeNavigation=Navigation;
		_.each(listeNavigation, function(navigation){
			$routeProvider.when(''+navigation.when, {templateUrl:navigation.templateUrl, controller:navigation.controller, view:navigation.view});
			$routeProvider.otherwise({redirectTo:'/login'});
		});
	}]);
	
})();




