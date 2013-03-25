define([
    'jquery',
    'moment',
    'backbone',
    'worklr',
    'app',
    'text!templates/CompletedSubRequest.html'
], function($, Moment, Backbone, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        initialize: function(options) {
            this.parentRequestId = options.parentRequestId;
        },

        modelEvents: {
            "change": "render"
        },

        onShow: function() {
            $(".data-object-label", this.el).popover({ trigger: 'click' });
        },

        onDomRefresh: function() {
            var that = this;
            Worklr.parseTimestamps();
        }

    });
});
