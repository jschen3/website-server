angular.module('app',['ngAnimate','ui.bootstrap']);
angular.module('app').controller('CarouselCtrl', function($scope, $http){
    
    $http.get("http://localhost:8080/Website/slides").then(function(response){
        $scope.slides = response.data;

        console.log($scope.slides);
    });
    
});
angular.module('app').controller('articleCtrl', function($scope, $http){
 /*   
    $scope.articles=[{
        title: "Article 1",
        dateNumbers: "01-22-2016",
        dateText: "January 22, 2016",
        text: "Here is some random text. Will be replaced with actual text shortly. Making templates work.",
        linkUrl: ""
    },
    {
        title: "Article 1",
        dateNumbers: "01-22-2016",
        dateText: "January 22, 2016",
        text: "Here is some random text. Will be replaced with actual text shortly. Making templates work.",
        linkUrl: ""
    }];
    */
    $http.get("http://localhost:8080/Website/articles").then(function(response){
        $scope.articles = response.data;
        console.log($scope.articles);
    });
    $scope.months=[{
        url: "",
        month:"January Articles"
    },
    {
        url: "",
        month: "February Articles"
    }];
});