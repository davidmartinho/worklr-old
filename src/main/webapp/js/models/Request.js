define([
    'backbone',
    'collections/Commentary',
    'collections/Request',
    'collections/DataObject'
], function(Backbone, CommentaryCollection, RequestCollection, DataObjectCollection) {

    return Backbone.Model.extend({

        urlRoot: "api/requests",

        parse: function(response) {
            var that = this;
            response.commentaries = new CommentaryCollection(response.commentaries);
            response.commentaries.request = this;
            response.commentaries.bind("add", function() {
                 that.trigger("change");
            });
            response.createdDataObjects = new DataObjectCollection(response.createdDataObjects);
            response.createdDataObjects.request = this;
            response.createdDataObjects.bind("add", function() {
                that.trigger("change");
            });
            return response;
        },

        isCompleted: function() {
            return this.get("completionTimestamp") !== undefined;
        },

        isUnclaimed: function() {
            return this.get("executor") === undefined;
        },

        hasInitiator: function(userId) {
            return this.get("initiator").id === userId;
        },

        hasExecutor: function(userId) {
            return this.get("executor") && this.get("executor").id === userId;
        },


        toJSON: function() {
            var json = {};
            _.extend(json, this.attributes);
            if(this.get("commentaries")) {
                _.extend(json, { commentaries: this.get("commentaries").toJSON() });
            }
            if(this.get("createdDataObjects")) {
                _.extend(json, { createdDataObjects: this.get("createdDataObjects").toJSON() });
            }
            return json;
        }
    });
});
