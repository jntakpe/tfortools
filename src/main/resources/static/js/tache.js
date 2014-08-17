var tacheApp = angular.module('tacheApp', ['ngResource', 'ngDragDrop', 'angularMoment']).constant('angularMomentConfig', {
    timezone: 'Europe/Paris'
}).run(function (amMoment) {
    "use strict";
    amMoment.changeLanguage('fr');
});

/**
 * Création de la factory permettant de gérer les appels au serveur en REST
 */
tacheApp.factory('TacheResource', ['$resource', function ($resource) {
    "use strict";
    return $resource('tache/:taskId', {taskId: '@id'}, {'update': {method: 'PUT'}});
}]);

/**
 * Contrôlleur gérant l'ajout, la modification et la suppression des tâches
 */
tacheApp.controller('tacheCtrl', ['$scope', 'TacheResource', function ($scope, TacheResource) {
    "use strict";

    /**
     * Renvoie la liste correspondant au statut d'une tâche
     * @param statut statut de la tâche
     * @returns {Array} liste de tâches
     */
    function findList(statut) {
        switch (statut) {
            case 'EN_STOCK':
                return $scope.stock;
            case 'EN_COURS':
                return $scope.progress;
            default:
                return $scope.done;
        }
    }

    /**
     * Récupère l'index d'une tâche dans une liste
     * @param task tâche recherchée
     * @param tasks liste des tâches
     */
    function getTaskIndex(task, tasks) {
        var idx;
        for (idx in tasks) {
            if (tasks.hasOwnProperty(idx)) {
                if (tasks[idx].id === task.id) {
                    return idx;
                }
            }
        }
    }

    /**
     * Ajout une tâche dans la liste appropriée en fonction du statut de la tâche
     * @param task tâche à ajouter
     */
    function add(task) {
        findList(task.statut).push(task);
    }

    /**
     * Met à jour une tâche dans sa liste
     * @param task tâche à mettre à jour
     */
    function refresh(task) {
        var tasks = findList(task.statut), idx = getTaskIndex(task, tasks);
        tasks[idx] = task;
    }

    /**
     * Supprime la tâche de sa liste
     * @param task tâche à supprimer
     */
    function remove(task) {
        var tasks = findList(task.statut), idx = getTaskIndex(task, tasks);
        tasks.splice(idx, 1);
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
                id: task.id,
                version: task.version,
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
    TacheResource.query(function (tasks) {
        var task;
        for (task in tasks) {
            if (tasks.hasOwnProperty(task) && tasks[task].id) {
                add(tasks[task]);
            }
        }
    });

    //Sauvegarde d'une tâche
    $scope.save = function (popupTask) {
        if (popupTask.id) {
            TacheResource.update(popupTask, function (task) {
                refresh(task);
            });
        } else {
            TacheResource.save(popupTask, function (task) {
                add(task);
            });
        }
    };

    //Mise à jour d'une tâche déplacée
    $scope.move = function (event) {
        var task = $scope.dndDragItem;
        task.statut = event.target.getAttribute('data-statut');
        TacheResource.update(task, function (persistedTask) {
            refresh(persistedTask);
        });
    };

    //Supprime une tâche
    $scope.remove = function (task) {
        task.$delete({}, function () {
            remove(task);
        });
    };

    //Gère la classe css de l'alerte
    $scope.alertClass = function (niveau) {
        var cssClass;
        switch (niveau) {
            case 'FAIBLE':
                cssClass = 'success';
                break;
            case 'NORMAL':
                cssClass = 'info';
                break;
            case 'IMPORTANT':
                cssClass = 'pending';
                break;
            default :
                cssClass = 'error';
                break;
        }
        return "alert-blocks alert-dismissable alert-blocks-" + cssClass;
    };

    //Gère la couleur du texte du titre
    $scope.color = function (niveau) {
        var color;
        switch (niveau) {
            case 'FAIBLE':
                color = 'color-light-green';
                break;
            case 'NORMAL':
                color = 'color-dark-blue';
                break;
            case 'IMPORTANT':
                color = 'color-yellow';
                break;
            default :
                color = 'color-red';
                break;
        }
        return color;
    };
}]);

