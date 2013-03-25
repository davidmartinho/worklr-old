define([
    'backbone',
    'models/Recommendation'
], function(Backbone, RecommendationModel) {

    return Backbone.Collection.extend({

        model: RecommendationModel

    });
});
