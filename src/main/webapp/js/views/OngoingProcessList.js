define([
    'jquery',
    'mustache',
    'backbone',
    'worklr',
    'views/OngoingProcessListItem',
    'views/EmptyOngoingProcessList',
    'text!templates/OngoingProcessList.html'
], function($, Mustache, Backbone, Worklr, OngoingProcessListItemView, EmptyOngoingProcessListView, tpl) {
    'use strict';

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        itemViewContainer: 'tbody',

        itemView: OngoingProcessListItemView,

        emptyView: EmptyOngoingProcessListView,

        events: {
            "click #completed-folder": "render"
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }

    });
});
