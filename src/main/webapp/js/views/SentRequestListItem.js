define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'text!templates/SentRequestListItem.html'
], function($, Mustache, Backbone, Moment, App, Router, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        tagName: 'tr',

        events: {
            "click .cancel-request-button": "cancelRequest"
        },

        cancelRequest: function(e) {
            e.preventDefault();
            var that = this;
            var requestModel = new Backbone.Model();
            requestModel.url = that.model.url()+"/cancel";
            requestModel.save(null, { success: function() {
                App.router.navigate("folders/sent", { trigger: true });
            }});
        }

    });
});