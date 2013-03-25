define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'router',
    'worklr',
    'views/ExecutingRequestListItem',
    'views/EmptyExecuting',
    'text!templates/ExecutingRequestList.html'
], function($, Mustache, Backbone, Moment, App, Router, Worklr, ExecutingRequestListItemView, EmptyExecutingView, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        itemViewContainer: 'tbody',

        itemView: ExecutingRequestListItemView,

        emptyView: EmptyExecutingView,

        onDomRefresh: function() {
            Worklr.parseTimestamps();
            $("a", this.el).tooltip();
        }
    });
});
