define([
    'jquery',
    'marionette',
    'text!templates/EmptyExecuting.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});