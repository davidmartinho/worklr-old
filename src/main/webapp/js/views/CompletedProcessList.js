define([
    'jquery',
    'mustache',
    'backbone',
    'worklr',
    'views/CompletedProcessListItem',
    'views/EmptyCompletedProcessList',
    'text!templates/CompletedProcessList.html'
], function($, Mustache, Backbone, Worklr, CompletedProcessListItemView, EmptyCompletedProcessListView, tpl) {
    'use strict';

    return Backbone.Marionette.CompositeView.extend({

    template: tpl,

        itemViewContainer: 'tbody',

        itemView: CompletedProcessListItemView,

        emptyView: EmptyCompletedProcessListView,

        events: {
            "click #completed-folder": "render"
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }

    });
});
