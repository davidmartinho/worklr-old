define([
    'jquery',
    'backbone',
    'app',
    'worklr',
    'views/OngoingProcessListItem',
    'views/EmptyOngoingProcessList',
    'text!templates/CompletedProcessList.html'
], function($, Backbone, App, Worklr, OngoingProcessListItemView, EmptyOngoingProcessList, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        itemViewContainer: 'tbody',

        itemView: OngoingProcessListItem,

        emptyView: EmptyOngoingProcessListView,

        events: {
            "click .complete-process-button": "completeProcess"
        },

        completeProcess: function(e) {
            e.preventDefault();
            var requestId = e.target.id;
            require(['models/Process'], function(ProcessModel) {
                var processModel = new ProcessModel();
                processModel.url = "api/processes/"+requestId+"/complete";
                processModel.save(null, {
                    success: function() {
                        App.router.navigate("processes/completed", { trigger: true });
                    }
                });
            });
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        },

        serializeData: function() {
            return {
                "processes": this.collection.toJSON()
            }
        },

        selectPill: function(pillClass) {
            $(".process-filter-pill").removeClass("active");
            $(pillClass).addClass("active");
        }
    });
});
