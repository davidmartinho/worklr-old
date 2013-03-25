define([
    'jquery',
    'jquery.ui',
    'jquery.tokeninput',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'text!templates/CompletedRequest.html'
], function($, jqueryUi, TokenInput, Mustache, Backbone, Moment, App, Router, Worklr, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        onShow: function() {
            $(".data-object-label", this.el).popover({ trigger: 'click' });
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }

    });
});
