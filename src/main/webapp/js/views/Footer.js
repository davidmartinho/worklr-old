define([
    'jquery',
    'mustache',
    'backbone',
    'router',
    'worklr',
    'text!templates/Footer.html'
], function($, Mustache, Backbone, Router, Worklr, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl

    });
});