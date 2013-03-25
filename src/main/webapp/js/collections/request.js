define([
    'backbone',
    'models/Request'
], function(Backbone, RequestModel){

    return Backbone.Collection.extend({

        model: RequestModel,

        comparator: function(request) {
            return -Date.parse(request.get("creationTimestamp"));
        }
    });
});