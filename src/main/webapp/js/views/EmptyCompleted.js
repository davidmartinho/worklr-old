define([
    'jquery',
    'marionette',
    'text!templates/EmptyCompleted.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});