define([
    'jquery',
    'backbone',
    'worklr',
    'views/InboxRequestListItem',
    'views/EmptyInbox',
    'text!templates/InboxRequestList.html'
], function($, Backbone, Worklr, InboxRequestListItemView, EmptyInboxView, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        itemViewContainer: 'tbody',

        itemView: InboxRequestListItemView,

        emptyView: EmptyInboxView,

        onShow: function() {
            $("a", this.el).tooltip();
        },

        onDomRefresh: function() {
            Worklr.parseTimestamps();
        }

    });
});
