define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/CompleteRequest.html'
], function($, Backbone, Marionette, Worklr, App, tpl) {
    'use strict';

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #respond-to-request": "completeRequest"
        },

        completeRequest: function(e) {
            e.preventDefault();
            var dataObjectsIds = [];
            $("input:checked", this.el).each(function() { dataObjectsIds.push(this.name) });
            var requestCompleteModel = new Backbone.Model();
            requestCompleteModel.url = this.model.url()+"/complete";
            requestCompleteModel.save({ outputDataObjectIds: dataObjectsIds }, {
                success: function() {
                    $('#modal-placeholder').modal('hide');
                    App.router.navigate("folders/completed", {trigger:true});
                },
                error: function(model, response) {
                    $('#modal-placeholder').modal('hide');
                    var msg = JSON.parse(response.responseText);
                    App.showNotification("Error", msg.message, "error");
                }
            });
        },

        onShow: function() {
            $('#modal-placeholder').modal();
        }
    });
});
