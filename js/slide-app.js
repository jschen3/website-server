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
        $scope.items = [{
            name: 'Scuba Diving Kit',
            id: 7297510
        },{
            name: 'Snorkel',
            id: 0278916
        },{
            name: 'Wet Suit',
            id: 2389017
        },{
            name: 'Beach Towel',
            id: 1000983
        }];
    }
    
    angular
        .module('app', [])
        .controller('MainCtrl', MainCtrl);

})();