define([
    'jquery',
    'backbone',
    'moment',
    'app',
    'worklr',
    'text!templates/UnclaimedRequest.html',
], function($, Backbone, Moment, App, Worklr, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        events: {
            "click #claim-request" : "claimRequest"
        },

        modelEvents: {
            "change": "render"
        },

        claimRequest: function(e) {
            e.preventDefault();
            var that = this;
            this.model.save({ claim: true }, { wait: true,
                success: function() {
                    App.showNotification("Info", "Request claimed successfully");
                    App.router.navigate("/", { replace: true, trigger: false }); //ugly stuff to refreshPage
                    App.router.navigate("requests/"+that.model.get("id"), { trigger: true });
                },
                error: function(model, response) {
                    var msg = JSON.parse(response.responseText);
                    App.showNotification("Error", msg.message, "error");
                }
            });
        },

        onDomRefresh: function() {
            var that = this;
            $(".data-object-label", this.el).tooltip();
            Worklr.parseTimestamps();
        }
    });
});
