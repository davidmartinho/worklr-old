define([
    'jquery',
    'moment',
    'backbone',
    'text!templates/CompletedProcessListItem.html'
], function($, Moment, Backbone, tpl) {
    'use strict';

    return Backbone.Marionette.ItemView.extend({
        tagName: 'tr',
        template: tpl
    });
});
