<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    </head>
<body>
    <div ng-app="myApp" ng-controller="customersCtrl">
    <table border="1">

        <tr ng-repeat="row in array">
            <td ng-repeat="item in row track by $index">{{item}}</td>
         </tr>

    </table>

    </div>

    <script src="myApp.js"></script>

</body>
</html>