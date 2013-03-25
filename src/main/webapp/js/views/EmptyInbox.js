define([
    'jquery',
    'marionette',
    'text!templates/EmptyInbox.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});