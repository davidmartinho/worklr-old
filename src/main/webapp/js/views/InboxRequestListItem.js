define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'text!templates/InboxRequestListItem.html'
], function($, Mustache, Backbone, Moment, App, Router, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        tagName: 'tr',

        template: tpl,

        events: {
            "click .claim-request-button": "claimRequest"
        },

        claimRequest: function(e) {
            var target = e.target;
            var requestId = target.id;
            var requestClaimModel = new Backbone.Model;
            requestClaimModel.url = "api/requests/"+requestId+"/claim";
            requestClaimModel.save(null, {
                success: function() {
                    App.router.navigate("folders/executing", {trigger:true});
                }
            });
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }
    });
});
