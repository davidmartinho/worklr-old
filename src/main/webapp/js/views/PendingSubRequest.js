define([
    'jquery',
    'jquery.ui',
    'moment',
    'backbone',
    'worklr',
    'app',
    'text!templates/PendingSubRequest.html'
], function($, jQueryUi, Moment, Backbone, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        initialize: function(options) {
            this.parentRequestId = options.parentRequestId;
        },

        modelEvents: {
            "change": "render"
        },

        events: {
            "click .cancel-sub-request": "cancelSubRequest"
        },

        cancelSubRequest: function(e) {
            var that = this;
            this.model.save({ cancel: true }, {
                success: function() {
                    App.showNotification("Info", "Sub-request canceled successfully");
                    App.router.navigate("/", { trigger: false, replace: true });
                    App.router.navigate("requests/"+that.parentRequestId, { trigger: true });
                },
                error: function(model, response) {
                    var msg = JSON.parse(response.responseText);
                    App.showNotification("Error", msg.message, "error");
                }
            });
        },

        onShow: function() {
            $(".data-object-label", this.el).popover({ trigger: 'click' });
        },

        onDomRefresh: function() {
            var that = this;
            Worklr.parseTimestamps();
            $(".sub-request-data").droppable({
                drop: function(event, ui) {
                    that.copyDataObject(ui.helper.context.id);
                }
            });
        },



        copyDataObject: function(dataObjectId) {
            var requestId = this.model.get("id");
            this.model.save({ provideData: true, dataObjectId: dataObjectId }, { wait: true,
                success: function() {
                    App.showNotification("Info", "Data object copied successfully", "info");
                },
                error: function(model, response) {
                    var msg = JSON.parse(response.responseText);
                    App.showNotification("Error", msg.message, "error");
                }
            });
        }

    });
});
