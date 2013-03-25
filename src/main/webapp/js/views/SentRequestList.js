define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'views/SentRequestListItem',
    'views/EmptySent',
    'text!templates/SentRequestList.html'
], function($, Mustache, Backbone, Moment, App, Router, Worklr, SentRequestListItemView, EmptySentView, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        itemViewContainer: 'tbody',

        itemView: SentRequestListItemView,

        emptyView: EmptySentView,

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        },

        serializeData: function() {
            return {
                "requests": this.collection.toJSON()
            }
        }
    });
});