var tacheApp = angular.module('tacheApp', ['ngResource', 'ngDragDrop']);

/**
 * Création de la factory permettant de gérer les appels au serveur en REST
 */
tacheApp.factory('TacheResource', ['$resource', function ($resource) {
    "use strict";
    return $resource('tache');
}]);

/**
 * Contrôlleur gérant l'ajout, la modification et la suppression des tâches
 */
tacheApp.controller('tacheCtrl', ['$scope', 'TacheResource', function ($scope, TacheResource) {
    "use strict";

    /**
     * Ajout une tâche dans la liste appropriée en fonction du statut de la tâche
     * @param task t^che à ajouter
     */
    function add(task) {
        if (task.statut === 'EN_STOCK') {
            $scope.stock.push(task);
        } else if (task.statut === 'EN_COURS') {
            $scope.progress.push(task);
        } else if (task.statut === 'TERMINE') {
            $scope.done.push(task);
        }
    }

    /**
     * Met à jour une tâche dans sa liste
     * @param task tâche à mettre à jour
     */
    function refresh(task) {
        var tasks, item;
        if (task.statut === 'EN_STOCK') {
            tasks = $scope.stock;
        } else if (task.statut === 'EN_COURS') {
            tasks = $scope.progress;
        } else if (task.statut === 'TERMINE') {
            tasks = $scope.done;
        }
        for (item in tasks) {
            if (tasks.hasOwnProperty(item)) {
                if (tasks[item].id === task.id) {
                    tasks[item] = task;
                }
            }
        }
    }

    $scope.stock = [];
    $scope.progress = [];
    $scope.done = [];

    /**
     * Initialise les champs de la popup
     *
     * @param statut le statut de la tâche courante
     * @param [task] la tâche courante
     */
    $scope.initPopup = function (statut, task) {
        if (task) {
            $scope.popupTask = {
                nom: task.nom,
                description: task.description,
                niveau: task.niveau,
                statut: statut
            };
        } else {
            $scope.popupTask = {
                nom: '',
                description: '',
                niveau: 'NORMAL',
                statut: statut
            };
        }
    };

//Récupère la liste de tâches de l'utilisateur courant
    TacheResource.query(function (response) {
        var task;
        for (task in response) {
            if (response.hasOwnProperty(task)) {
                add(response[task]);
            }
        }
    });

//Création d'une nouvelle tâche
    $scope.create = function (newTask) {
        newTask.statut = $scope.currentAddPopup;
        TacheResource.save(newTask, function (response) {
            add(response);
        });
    };

//Sauvegarde la tâche déplacée
    $scope.move = function (event) {
        var task = $scope.dndDragItem;
        task.statut = event.target.getAttribute('data-statut');
        TacheResource.save(task, function (response) {
            refresh(response);
        });
    };

}]);

