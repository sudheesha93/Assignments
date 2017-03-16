.controller('RegisterController', function ($scope, $cordovaDatePicker,$filter, $ionicPlatform) {
    
    
//     $scope.dob = {
//        date: ''
//        
//    };
    
    
    // DatePicker Plugin
    $ionicPlatform.ready(function(){
    $scope.showDatePicker = function(){
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

       $cordovaDatePicker.show(options).then(function(date){
         var a=date;
        $scope.dob=$filter('date')(a, 'EEE, dd MMM yyyy');
           
        });
   };
 });
    
  });