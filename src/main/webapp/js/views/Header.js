define([
    'jquery',
    'marionette',
    'text!templates/Header.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        onShow: function() {
            $('.dropdown-toggle').dropdown();
        },

        selectMenuItem : function(menuItem) {
            $('.nav li').removeClass('active');
            if (menuItem) {
                $('.' + menuItem).addClass('active');
            }
        }
    });
});