define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'router',
    'worklr',
    'text!templates/Process.html'
], function($, Mustache, Backbone, Moment, Router, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        },

        onShow: function() {
            $("a", this.el).tooltip();
        }

    });
});
