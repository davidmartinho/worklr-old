define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/ForwardRequest.html'
], function($, Backbone, Marionette, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #forward-request": "forwardRequest"
        },

        forwardRequest: function(e) {
            e.preventDefault();
            var previousId = this.model.get("id");
            var subject = $('input[name=subject]', this.el).val();
            var description = $('textarea[name=description]', this.el).val();
            var queues = $('input[name=queues]', this.el).val().split(",");
            var inputDataObjects = [];
            $("input[name='inputDataObjects[]']:checked").each(function () {
                inputDataObjects.push($(this).val());
            });
            require(['models/Request'], function(RequestModel) {
                var forwardRequestModel = new RequestModel({
                    subject: subject,
                    description: description,
                    queues: queues,
                    inputDataObjects: inputDataObjects });
                forwardRequestModel.url = "api/requests/"+previousId+"/forward";
                forwardRequestModel.save(null, { success: function() {
                    $('#modal-placeholder').modal('hide');
                    App.router.navigate("#folders/sent",  { trigger: true });
                }});
            });
        },

        onShow: function() {
            var queueCollection = Worklr.queueCollection;
            queueCollection.fetch({ success: function() {
                $("#demo-input-facebook-theme").tokenInput(queueCollection.toJSON(), {
                    theme: "facebook",
                    preventDuplicates: true,
                    hintText: "Search for a Queue Name..."
                });
            }});
            $('#modal-placeholder').modal('show');
        }

    });
});
