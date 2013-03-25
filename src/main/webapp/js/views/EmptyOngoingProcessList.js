define([
    'jquery',
    'marionette',
    'text!templates/EmptyOngoingProcessList.html'
], function($, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});
