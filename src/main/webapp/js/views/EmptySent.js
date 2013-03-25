define([
    'jquery',
    'marionette',
    'text!templates/EmptySent.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});