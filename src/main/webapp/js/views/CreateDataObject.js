define([
    'jquery',
    'mustache',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/CreateDataObject.html'
], function($, Mustache, Backbone, Marionette, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #create-data-object": "createDataObject"
        },

        createDataObject: function(e) {
            e.preventDefault();
            var that = this;
            var type =  $(":selected", this.el).val();
            var label = $("input[name='label']", this.el).val();
            if(type === "text") {
                var value = $("input[name='value']", this.el).val();
                require(['models/DataObject'], function(DataObjectModel) {
                    var dataObjectModel = new DataObjectModel();
                    dataObjectModel.url = that.model.url()+"/data";
                    dataObjectModel.save({
                        type: type,
                        label: label,
                        value: value
                    }, { success: function() {
                        that.model.get("createdDataObjects").add(dataObjectModel);
                        $('#modal-placeholder').modal('hide');
                    }});
                });
            } else {
                var file = $("input[name='file']", this.el).val();
            }
        },

        onShow: function() {
            $('#modal-placeholder').modal();
            $('select[name=type]').on("change", function() {
                var type = $(this).val();
                var textInput = "<input type='text' class='input-block-level' placeholder='Valor' name='value' />";
                var fileUpload = "<input name='file' type='file' />";
                switch(type) {
                    case 'file': $('input[name=value]').replaceWith(fileUpload); break;
                    case 'text': $('input[name=file]').replaceWith(textInput); break;
                }
            });
        }
    });
});
