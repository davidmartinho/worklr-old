define([
    'jquery',
    'jquery.ui',
    'jquery.tokeninput',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'text!templates/ExecutingRequest.html'
], function($, jqueryUi, TokenInput, Mustache, Backbone, Moment, App, Router, Worklr, tpl) {

    return Backbone.Marionette.Layout.extend({

        template: tpl,

        events: {
            "click #respond-to-request" : "respondToRequest",
            "click #create-sub-request" : "createSubRequest",
            "click #forward-request" : "forwardRequest",
            "click .edit-data-object" : "editDataObject",
            "click .create-data-object" : "createDataObject",
            "click #post-comment" : "postComment",
            "click .show-sub-request": "showSubRequest"
        },

        modelEvents: {
            "change": "render"
        },

        regions: {
            subRequestContainer: "#sub-request-container"
        },

        showSubRequest: function(e) {
            e.preventDefault();
            var that = this;
            var requestId = e.currentTarget.id;
            console.log(requestId);
            $(".show-sub-request").parent().removeClass("active");
            $(e.currentTarget).parent().addClass("active");
            require(['models/Request',
                'views/PendingSubRequest',
                'views/ExecutingSubRequest',
                'views/CompletedSubRequest'], function(RequestModel, PendingSubRequestView, ExecutingSubRequestView, CompletedSubRequestView) {
                var subRequestModel = new RequestModel({id: requestId });
                subRequestModel.fetch({ success: function() {
                    if(subRequestModel.isUnclaimed()) {
                        that.subRequestContainer.show(new PendingSubRequestView({ model: subRequestModel, parentRequestId: that.model.get("id") }));
                    } else if(subRequestModel.isCompleted()) {
                        that.subRequestContainer.show(new CompletedSubRequestView({ model: subRequestModel, parentRequestId: that.model.get("id") }));
                    } else {
                        that.subRequestContainer.show(new ExecutingSubRequestView({ model: subRequestModel, parentRequestId: that.model.get("id") }));
                    }
                }});
            });
        },

        editDataObject: function(e) {
            e.preventDefault();
            var that = this;
            var dataObjectId = $(e.currentTarget).data("id");
            require(['models/DataObject', 'views/EditDataObject'], function(DataObjectModel, EditDataObjectView) {
                var dataObjectModel = new DataObjectModel({ id: dataObjectId });
                dataObjectModel.fetch({ success: function() {
                    App.layouts.headerContentFooter.modalRegion.show(new EditDataObjectView({ request: that.model, dataObject: dataObjectModel }));
                }});
            });
        },

        forwardRequest: function(e) {
            e.preventDefault();
            var that = this;
            require(['views/ForwardRequest'], function(ForwardRequestView) {
                App.layouts.headerContentFooter.modalRegion.show(new ForwardRequestView({ model: that.model }));
            });
        },

        respondToRequest: function(e) {
            e.preventDefault();
            var requestId = e.target.id;
            var that = this;
            require(['views/CompleteRequest'], function(CompleteRequestView) {
                App.layouts.headerContentFooter.modalRegion.show(new CompleteRequestView({ model: that.model }));
            });
        },

        createSubRequest: function(e) {
            e.preventDefault();
            var that = this;
            var requestId = e.target.id;
            require(['views/CreateSubRequest', 'collections/Recommendation'], function(CreateSubRequestView, RecommendationCollection) {
                var recommendationCollection = new RecommendationCollection();
                recommendationCollection.url = that.model.url()+"/recommendations";
                recommendationCollection.fetch({
                    success: function() {
                        App.layouts.headerContentFooter.modalRegion.show(new CreateSubRequestView({ request: that.model, recommendations: recommendationCollection }));
                    }
                })
            });
        },

        createDataObject: function(e) {
            e.preventDefault();
            var that = this;
            require(['views/CreateDataObject'], function(CreateDataObjectView) {
                App.layouts.headerContentFooter.modalRegion.show(new CreateDataObjectView({ model: that.model }));
            });
        },

        postComment: function(e) {
            e.preventDefault();
            var that = this;
            var requestId = e.target.getAttribute("data-request-id");
            //var textValue = $('textarea[name=commentary]', this.el).val();
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

        onDomRefresh: function() {
            var that = this;
            Worklr.parseTimestamps();
            $(".data-object-label", this.el).popover({ trigger: 'click' });
            $(".data-object-label").draggable({ revert: true, zIndex: 500, helper: "clone" });
        },

        serializeData: function() {
            return {
                "request": this.model.toJSON(),
                "executor": Worklr.profileModel.toJSON(),
                "queues": Worklr.queueCollection.toJSON()
            }
        }
    });
});
