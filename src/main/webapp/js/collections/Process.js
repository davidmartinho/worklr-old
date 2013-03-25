define([
    'backbone',
    'models/Process'
], function(Backbone, ProcessModel) {

    return Backbone.Collection.extend({

        model: ProcessModel,

        url: "api/processes"

    });
});