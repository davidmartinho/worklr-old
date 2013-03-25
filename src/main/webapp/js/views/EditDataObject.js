define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/EditFileDataObject.html',
    'text!templates/EditStringDataObject.html'
], function($, Backbone, Marionette, Worklr, App, editFileDataObjectTpl, editStringDataObjectTpl) {

    return Backbone.Marionette.ItemView.extend({

        initialize: function(model) {
            this.request = model.request;
            this.dataObject = model.dataObject;
        },

        onShow: function() {
            $('#modal-placeholder').modal('show');
        },

        events: {
            "click #update-data-object": "updateDataObject"
        },

        updateDataObject: function(e) {
            var newValue = $('input[name=value]', this.el).val();
            var that = this;
            this.dataObject.save({ value: newValue }, {
                success: function() {
                    $('#modal-placeholder').modal('hide');
                    App.router.navigate("/", { replace: true, trigger: false }); //ugly stuff to refreshPage
                    App.router.navigate("requests/"+that.request.get("id"), { trigger: true });
                }
            });
        },

        getTemplate: function() {
            console.log(this.dataObject.get("isFile"));
            if(this.dataObject.get("isFile")) {
                return editFileDataObjectTpl;
            } else {
                return editStringDataObjectTpl;
            }
        },

        serializeData: function() {
            return this.dataObject.toJSON();
        }

    });
});
