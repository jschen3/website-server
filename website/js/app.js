angular.module('app',['ngAnimate','ui.bootstrap', 'Constants']);
angular.module('app').controller('CarouselCtrl', ['$scope', '$http', 'SLIDE_URL', function($scope, $http, slideUrl){

    $http.get(slideUrl).then(function(response){
        $scope.slides = response.data;

        console.log($scope.slides);
    });
    
}]);
angular.module('app').controller('articleCtrl', ['$scope', '$http', 'ARTICLE_URL', function($scope, $http, articleUrl){
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
    $http.get(articleUrl).then(function(response){
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
}]);
