angular.module('app',['ngAnimate','ui.bootstrap']);
angular.module('app').controller('CarouselCtrl', function($scope, $http){
    $scope.slides = [{
            image: "images/intro-picture.jpg",
            title: "Introduction",
            text: "My name is Jimmy Chen and welcome to my website. I am an enterprising software developer and my current projects and articles posted on this page have been about machine learning and data mining. To learn more about me click the about me button. To find my resume click the resume button",
            links:[
                {url: "resume.html", text:"Resume"},
                {url: "", text: "About me"}
            ]
        },
        {
            image: "images/intro-picture.jpg",
            title: "Introduction",
            text: "My name is Jimmy Chen and welcome to my website. I am an enterprising software developer and my current projects and articles posted on this page have been about machine learning and data mining. To learn more about me click the about me button. To find my resume click the resume button",
            links:[
                {url: "resume.html", text:"Resume"},
                {url: "", text: "About me"}
            ]
    }];
    console.log($scope.slides);
    
    $http.get("http://localhost:8080/Website/slides").then(function(response){
        $scope.slides = response.data;

        console.log($scope.slide2);
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
    $http.get("http://localhost:8080/Website/articleblurbs").then(function(response){
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