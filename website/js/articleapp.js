angular.module('articleapp',[]);
angular.module('articleapp').controller('ArticleCtrl', function($scope, $http){
	$http.get("http://localhost:8080/Website/articles").then(function(response){
		$scope.article = response.data;
		console.log($scope.article);
	});
});