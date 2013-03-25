define([
    'backbone',
    'models/Queue'
], function(Backbone, QueueModel) {

    return Backbone.Collection.extend({

        model: QueueModel,

        url: "api/queues"

    });
});