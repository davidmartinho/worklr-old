define([
    'jquery',
    'moment',
    'backbone',
    'worklr',
    'app',
    'text!templates/ExecutingSubRequest.html'
], function($, Moment, Backbone, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #comment-sub-request": "commentSubRequest"
        },

        modelEvents: {
            "change": "render"
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

        commentSubRequest: function(e) {
            e.preventDefault();
            var that = this;
            var requestId = e.target.getAttribute("data-request-id");
            var textValue = $('#comment-'+requestId, this.el).val();
            if(textValue === "") {
                App.showNotification("Error", "You must write something in the comment", "error");
            } else {
                require(['models/Commentary'], function(CommentaryModel) {
                    var newComment = new CommentaryModel();
                    newComment.url = "api/requests/"+requestId+"/comments";
                    newComment.save({ text: textValue }, {
                        success: function() {
                            that.model.get("commentaries").add(newComment);
                        }
                    })});
            }
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
