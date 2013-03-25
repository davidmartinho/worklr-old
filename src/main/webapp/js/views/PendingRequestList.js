define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'router',
    'worklr',
    'text!templates/PendingRequestList.html'
], function($, Mustache, Backbone, Moment, Router, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        },

        serializeData: function() {
            return {
                "requests": this.collection.toJSON()
            }
        }

    });
});