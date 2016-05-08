/**
 * Created by 310031267 on 2016/5/8.
 */
(function(){
    'user strict';

    angular.module('resumeApp')
        .directive('scrollTo', scrollTo);

    scrollTo.$inject = ['$anchorScroll'];
    function scrollTo($anchorScroll){
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                element.bind('click', function (event) {
                   event.preventDefault();
                    event.stopPropagation();
                    var location = attrs.scrollTo;
                    $anchorScroll(location);
                });
            }
        };
    }

})();
