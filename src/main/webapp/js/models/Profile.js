define([
    'backbone',
], function(Backbone) {

    return Backbone.Model.extend({

        urlRoot: "api/profile",

        methodToURL : {
            'read' : 'api/profile',
            'update' : 'api/profile/'
        },

        sync : function(method, model, options) {
            options = options || {};
            options.url = model.methodToURL[method.toLowerCase()];

            Backbone.sync(method, model, options);
        }

    });
});