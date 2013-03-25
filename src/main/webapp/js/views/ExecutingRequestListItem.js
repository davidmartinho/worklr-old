define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'worklr',
    'text!templates/ExecutingRequestListItem.html'
], function($, Mustache, Backbone, Moment, App, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        tagName: 'tr',

        template: tpl,

        events: {
            "click .respond-request-button": "respondRequest"
        },

        respondRequest: function(e) {
            e.preventDefault();
            var requestId = e.target.id;
            var requestModel = Worklr.requestModel;
            requestModel.urlRoot = "api/requests/"+requestId+"/respond";
            requestModel.save(null, {
                success: function() {
                    App.router.navigate("#folders/completed",true);
                }
            });
        }

    });
});
