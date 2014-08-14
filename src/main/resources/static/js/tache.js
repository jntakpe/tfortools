var tacheApp = angular.module('tacheApp', ['ngResource', 'ngDragDrop']);

tacheApp.factory('TacheService', ['$resource', function ($resource) {
    "use strict";
    return $resource('tache');
}]);

tacheApp.controller('tacheCtrl', ['$scope', 'TacheService', function ($scope, TacheService) {
    "use strict";
    $scope.tasks = TacheService.query();

    $scope.create = function (task) {
        task.statut = 'EN_COURS';
        task.niveau = 'FAIBLE';
        TacheService.save(task);
    };

}]);

