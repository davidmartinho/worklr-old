define([
    'jquery',
    'mustache',
    'backbone',
    'worklr',
    'views/CompletedRequestListItem',
    'views/EmptyCompleted',
    'text!templates/CompletedRequestList.html'
], function($, Mustache, Backbone, Worklr, CompletedRequestListItemView, EmptyCompletedView, tpl) {
    'use strict';

    return Backbone.Marionette.CompositeView.extend({

    template: tpl,

        itemViewContainer: 'tbody',

        itemView: CompletedRequestListItemView,

        emptyView: EmptyCompletedView,

        events: {
            "click #completed-folder": "render"
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }

    });
});