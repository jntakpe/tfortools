var tacheApp = angular.module('tacheApp', ['ngResource', 'ngDragDrop']);

tacheApp.factory('TacheService', ['$resource', function ($resource) {
    "use strict";
    return $resource('tache');
}]);

tacheApp.controller('tacheCtrl', ['$scope', 'TacheService', function ($scope, TacheService) {
    "use strict";
    var test = TacheService.query();
    console.log(test);
    $scope.stockTasks = [];
    $scope.progressTasks = [];
    $scope.doneTasks = [];

    $scope.stockTasks = [
        {'nom': 'test', 'description': 'coucou'},
        {'nom': 'test2', 'description': 'coucou2'},
        {'nom': 'test3', 'description': 'coucou3'}
    ];

    $scope.progressTasks = [
        {'nom': 'lot', 'description': 'lotir lemon'}
    ];
}]);

