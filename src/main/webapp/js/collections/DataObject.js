define([
    'backbone',
    'models/DataObject'
], function(Backbone, DataObjectModel) {

    return Backbone.Collection.extend({

        model: DataObjectModel,

        url: function() {
            return this.request.url()+"/data";
        },

        comparator: function(comment) {
            return Date.parse(comment.get("timestamp"));
        }
    });
});
