define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/CreateSubRequest.html'
], function($, Backbone, Marionette, Worklr, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #send-request": "sendRequest"
        },

        initialize: function (model) {
            this.request = model.request;
            this.recommendations = model.recommendations;
        },

        sendRequest: function(e) {
            e.preventDefault();
            var parentId = this.request.get("id");
            var subject = $('input[name=subject]', this.el).val();
            var description = $('textarea[name=description]', this.el).val();
            var queues = $('input[name=queues]', this.el).val().split(",");
            var inputDataObjects = [];
            $("input[name='inputDataObjects[]']:checked").each(function () {
                inputDataObjects.push($(this).val());
            });
            require(['models/Request'], function(RequestModel) {
                var subRequestModel = new RequestModel({
                    subject: subject,
                    description: description,
                    queues: queues,
                    inputDataObjects: inputDataObjects });
                subRequestModel.url = "api/requests/"+parentId+"/subrequest";
                subRequestModel.save(null, { success: function() {
                    $('#modal-placeholder').modal('hide');
                    App.router.navigate("#folders/sent",  { trigger: true });
                }});
            });
        },


        onShow: function() {
            console.log(this.recommendations);
            var that = this;
            var queueCollection = Worklr.queueCollection;
            queueCollection.fetch({ success: function() {
                $("#demo-input-facebook-theme").tokenInput(queueCollection.toJSON(), {
                    theme: "facebook",
                    preventDuplicates: true,
                    hintText: "Search for a Queue Name..."
                });
            }});
            $('#modal-placeholder').modal('show');
            $('#subject-labels').on("change", function() {
                if($(this).val() !== "") {
                    var recommendations = that.recommendations.toJSON();
                    for(var i in recommendations) {
                        var recommendation = recommendations[i];
                        for(var possibleSubjectId in recommendation.possibleSubjects) {
                            var possibleSubject = recommendation.possibleSubjects[possibleSubjectId];
                            if(possibleSubject === $(this).val()) {
                                that.selectRecommendation(possibleSubject, recommendation);
                            }
                        }
                    }
                } else {
                    that.clearRecommendation();
                }
            });
        },

        clearRecommendation: function() {
            $("#demo-input-facebook-theme").tokenInput("clear");
            $('input[name=subject]', this.el).val("");
        },

        selectRecommendation: function(possibleSubject, recommendation) {
            var queues = recommendation.queues;
            console.log(queues);
            $("#demo-input-facebook-theme").tokenInput("clear");
            for(var queueId in queues) {
                $("#demo-input-facebook-theme").tokenInput("add", queues[queueId]);
            }
            $('input[name=subject]', this.el).val(possibleSubject);
            var inputDataObjectLabels = recommendation.inputDataObjectLabels;
            $("input[name='inputDataObjects[]']").each(function () {
                if($.inArray($(this).data("label"), inputDataObjectLabels) >= 0) {
                    console.log($(this).data("label"));
                    console.log(inputDataObjectLabels);
                    $(this).prop("checked", true);
                } else {
                    $(this).prop("checked", false);
                }
            });
            $('textarea[name=description]', this.el).focus();
        },

        serializeData: function() {
            return {
                "request": this.request.toJSON(),
                "recommendations": this.recommendations.toJSON()
            }
        }

    });
});
