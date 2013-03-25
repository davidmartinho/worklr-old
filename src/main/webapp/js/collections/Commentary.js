define([
    'backbone',
    'models/Commentary'
], function(Backbone, CommentaryModel) {

    return Backbone.Collection.extend({

        model: CommentaryModel,

        url: function() {
            return this.request.url()+"/comments";
        },

        comparator: function(comment) {
            return Date.parse(comment.get("timestamp"));
        }
    });
});