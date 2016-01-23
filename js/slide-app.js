(function () {

    function MainCtrl ($scope) {
        $scope.slides = [{
            image: "images/intro-picture.jpg",
            title: "Introduction",
            text: "My name is Jimmy Chen and welcome to my website. I am an enterprising software developer and my current projects and articles posted on this page have been about machine learning and data mining. To learn more about me click the about me button. To find my resume click the resume button",
            link1url:"",
            link2url:"",
            link1text:"Resume",
            link2text:"About Me"
        },{
            image: "images/intro-picture.jpg",
            title: "Introduction",
            text: "My name is Jimmy Chen and welcome to my website. I am an enterprising software developer and my current projects and articles posted on this page have been about machine learning and data mining. To learn more about me click the about me button. To find my resume click the resume button",
            link1url:"",
            link2url:"",
            link1text:"Resume",
            link2text:"About Me"
        }];
    }
    
    angular
        .module('app', [])
        .controller('MainCtrl', MainCtrl);

})();