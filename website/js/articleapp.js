/*var module=angular.module('articleapp',['ngRoute']);
	module.config(function($routeProvider){
		$routeProvider.when("/:id",{
			templateUrl: "article.html",
			controller: "ArticleCtrl"
		});
	});
	module.controller('ArticleCtrl', function($scope, $http, $routeParams){
	console.log($routeParams);
	$http.get("http://localhost:8080/Website/articles/"+$routeParams.id).then(function(response){
		$scope.article = response.data;
		console.log($scope.article);
	});
});
*/
var app = angular.module('articleapp', ['Constants']);
app.controller("ArticleCtrl", ['$scope', '$http', '$location', 'ARTICLE_URL', function ($scope, $http, $location, articleUrl) {
      // do stuff with $routeParams
    $scope.id=$location.search().id;
    console.log($location.search().id);
    $http.get(articleUrl+"/"+$scope.id).then(function(response){
      	$scope.article = response.data;
		console.log($scope.article);
	});
}]);