(function(){ 
  'use strict';
  var mod = angular.module('global.service');

//===>information for local machine, your computer
  mod.constant('serverConfig', {
    protocal:'http://', 
    domain:'localhost',
    context:'', 
    port:8080
  });

//===>information for the server who provides REST service
  mod.constant('serverRESTConfig', {
    protocal:'http://', 
    domain:'localhost',
    port:8080, 
    context:'/FindEvent/rest/'
  });
  
})();