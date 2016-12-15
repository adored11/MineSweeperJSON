var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {

    $http.get('http://localhost:8080/WebApp1Servlet').then(function(response){
        $scope.games = response.data.line;
        /*$scope.rows = data.rows;
        $scope.cols = data.columns;*/
        $scope.elements = [];
        $scope.arrayElements = [];
        $scope.formatArray = [];
        $scope.array = [];
        $scope.gameBoard = response.data.line;
        $scope.numRows = response.data.NumRows;
        $scope.numCols = response.data.RowLength;
        //$scope.formatArray = $scope.gameBoard.join("");

        angular.forEach($scope.gameBoard, function(item){
            $scope.elements = item;
            var uniqueSquare = [];
            for (var i = 0; i<$scope.elements.length;i++ ){
                if(uniqueSquare.indexOf($scope.elements[i]) == -1)
                    uniqueSquare.push($scope.elements[i]);
                    $scope.arrayElements.push(uniqueSquare);
            }

        })
        $scope.formatArray = $scope.arrayElements.join("");
        //var size = $scope.numCols;
        //var arr = $scope.arrayElements;
        var chunk = function(arr, size) {
           var newArr = [];
              for (var i=0; i<arr.length; i+=size) {
                  newArr.push(arr.slice(i, i+size));
              }
           return newArr;
        };

        $scope.array = chunk($scope.gameBoard, $scope.numCols);

    });
    /*$scope.formatRows = function (){
        var numRows = $scope.numRows;
        var numCols = $scope.numCols;
        var gameArray = $scope.arrayElements;
        int gridSize = (numRows * numCols);
        var line ='';
        int count = 0;
        int arrayPosition = 0;

            for(int i=1; i<=gridSize; i++){
                if(i<=numCols){
                line = line + gameArray[i];
                count = count +1;
                }
                else{
                    line = line + gameArray[(count +i)];
                    count = count +1;
                }

            }

    }*/
});