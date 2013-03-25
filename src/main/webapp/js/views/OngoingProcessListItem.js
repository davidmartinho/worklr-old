define([
    'jquery',
    'moment',
    'backbone',
    'app',
    'text!templates/OngoingProcessListItem.html'
], function($, Moment, Backbone, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        tagName: 'tr',

        template: tpl,

        events: {
            "click .complete-process-button": "completeProcess"
        },

        completeProcess: function(e) {
            console.log(this.model);
            this.model.save({ complete: true }, {
                success: function() {
                    App.router.navigate("processes/completed", { trigger: true });
                }
            });
        }

    });
});
