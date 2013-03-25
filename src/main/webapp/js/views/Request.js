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
    'text!templates/CompletedRequest.html',
    'text!templates/UnclaimedRequest.html',
    'text!templates/ExecutingRequest.html'
], function($, jqueryUi, TokenInput, Mustache, Backbone, Moment, App, Router, Worklr, completedRequestTpl, unclaimedRequestTpl, executingRequestTpl) {

    return Backbone.Marionette.CompositeView.extend({

        events: {
            "click #claim-request" : "claimRequest",
            "click #respond-to-request" : "respondToRequest",
            "click #create-sub-request" : "createSubRequest",
            "click #forward-request" : "forwardRequest",
            "click .data-label" : "editDataObject",
            "click .create-data-object" : "createDataObject",
            "click #upload-data-object" : "uploadDataObject",
            "click #send-request" : "sendRequest",
            "click #post-comment" : "postComment"
        },

        modelEvents: {
            "change": "render"
        },

        templateHelpers: {
            canClaim: function() {
                return !this.request.executor;
            }
        },

        editDataObject: function(e) {
            e.preventDefault();
            var dataObjectId = e.target.id;
            require(['models/DataObject', 'views/EditDataObject'], function(DataObjectModel, EditDataObjectView) {
                var dataObjectModel = new DataObjectModel({ id: dataObjectId });
                dataObjectModel.fetch({ success: function() {
                    App.layouts.headerContentFooter.modalRegion.show(new EditDataObjectView({ model: dataObjectModel }));
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

        claimRequest: function(e) {
            e.preventDefault();
            var requestId = this.model.get("id");
            var requestClaimModel = new Backbone.Model;
            requestClaimModel.url = "api/requests/"+requestId+"/claim";
            requestClaimModel.save(null, {
                success: function() {
                    Backbone.history.navigate("requests/"+requestId, {trigger:true});
                },
                error: function(model, response) {
                    var msg = JSON.parse(response.responseText);
                    App.showNotification("Error", msg.message, "error");
                }
            });
        },

        uploadDataObject: function(e) {
            e.preventDefault();
            var labelText = $('input[name=label]', this.el).val();
            var valueText = $('input[name=value]', this.el).val();
        },

        getTemplate: function() {
            if(this.model.get("completionTimestamp")) {
                return completedRequestTpl;
            } else if(this.model.get("initiator").id === Worklr.profileModel.get("id")) {
                if(this.model.get("executor")) {
                    return executingRequestTpl;
                } else {
                    return unclaimedRequestTpl;
                }
            } else if(this.model.get("executor") && (this.model.get("executor").id === Worklr.profileModel.get("id"))) {
                return executingRequestTpl;
            } else {
                return unclaimedRequestTpl;
            }
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
            $('input[name=subject]', this.el).typeahead({
               source: ['Recommendation1', 'Recommendation2'],
               updater: function(item) {
                   $('textarea[name=description]', that.el).val("olaaa");
                   return item;
               }
            });
            $(".dragstuff").draggable({ revert: true, zIndex: 500, helper: "clone" });
            $(".dropstuff").droppable({
                drop: function( event, ui ) {
                    $(ui.helper).clone(true).removeAttr("style").appendTo($(this));
                }});
        },

        onShow: function() {
            $(".data-object-label", this.el).tooltip();
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
