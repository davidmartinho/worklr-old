define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/Notification.html'
], function($, Backbone, Marionette, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        initialize: function(options) {
            this.type = options.type || "info";
            this.title = options.title;
            this.message = options.message;
        },

        onShow: function() {
            $(".alert").addClass("alert-"+this.type);
            window.setTimeout(function() { $(".alert").delay(2000).slideUp('slow', function() {
                $(this).alert('close');
            })});
        },

        serializeData: function() {
            return {
                "title": this.title,
                "message": this.message
            }
        }

    });
});
