define([
    'jquery',
    'marionette',
    'text!templates/EmptyCompletedProcessList.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});
