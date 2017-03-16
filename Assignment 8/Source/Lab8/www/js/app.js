// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'

module.controller('RegisterController', function($scope, $cordovaDatePicker) {

  var options = {
    date: new Date(),
    mode: 'date', // or 'time'
    minDate: new Date() - 10000,
    allowOldDates: true,
    allowFutureDates: false,
    doneButtonLabel: 'DONE',
    doneButtonColor: '#F2F3F4',
    cancelButtonLabel: 'CANCEL',
    cancelButtonColor: '#000000'
  };

  document.addEventListener("deviceready", function () {

    $cordovaDatePicker.show(options).then(function(date){
        $scope.displayDate=date;
    });

  }, false);
});

.controller("ExampleController", function($scope, $rootScope, $ionicPlatform, $cordovaBatteryStatus) {
 
    $ionicPlatform.ready(function() {
        $rootScope.$on("$cordovaBatteryStatus:status", function(event, args) {
            if(args.isPlugged) {
                alert("Charging -> " + args.level + "%");
            } else {
                alert("Battery -> " + args.level + "%");
            }
        });
    });
 
});

angular.module('ngCordova.plugins.splashscreen', [])

  .factory('$cordovaSplashscreen', [function () {

    return {
      hide: function () {
        return navigator.splashscreen.hide();
      },

      show: function () {
        return navigator.splashscreen.show();
      }
    };

  }]);



var example = angular.module('starter', ['ionic', 'ngSanitize']);
 
example.run(function($ionicPlatform) {
    $ionicPlatform.ready(function() {
        if(window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            cordova.plugins.Keyboard.disableScroll(true);
        }
        if(window.StatusBar) {
            StatusBar.styleDefault();
        }
    });
});
 
example.controller("ExampleController", function($scope) {
    $scope.myHTML = 'Check out my programming <a href="https://www.thepolyglotdeveloper.com">blog</a> while you are here';
});



$scope.userinformation = {
        userName: '',
        password: ''
    };
    
	ionic.Platform.ready(function(){

  	});

  	$scope.login = function() {
  		
  		if ($scope.userinformation.userName!=''
            &&  $scope.userinformation.password!=''){
        alert('value of username:'+$scope.userinformation.userName);
            $state.go('tab');
        }

  		else{
            
            alert('Enter the Inputs');
        }
  	}
    
    $scope.register = function() {
    
       $state.go('register');
        }
   
    //Cardova Clipboard Plugin
    
    $scope.copyText = function(value){
        
          $cordovaClipboard
    .copy(value)
    .then(function () {
      // success
              alert('Copied Successfully');
    }, function () {
      // error
              
              alert('Copied failed');
    });
