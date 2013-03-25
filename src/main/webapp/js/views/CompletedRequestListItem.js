define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'app',
    'worklr',
    'text!templates/CompletedRequestListItem.html'
], function($, Mustache, Backbone, Moment, App, Worklr, tpl) {
    'use strict';

    return Backbone.Marionette.ItemView.extend({

        tagName: 'tr',

        template: tpl


    });
});