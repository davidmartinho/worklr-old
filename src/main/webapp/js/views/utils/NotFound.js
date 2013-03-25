define([
    'jquery',
    'mustache',
    'backbone',
    'router',
    'worklr',
    'text!templates/utils/NotFound.html'
], function($, Mustache, Backbone, Router, Worklr, NotFoundTemplate) {

    var NotFoundView = Backbone.View.extend({

        el: $('#header'),

        render : function() {
            var compiledTemplate = Mustache.compile(NotFoundTemplate);
            this.$el.html(compiledTemplate);
            return this;
        }

    });

    return NotFoundView;
});