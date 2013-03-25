define([
    'jquery',
    'backbone',
    'marionette',
    'worklr',
    'app',
    'text!templates/CreateProcess.html'
], function($, Backbone, Marionette, Worklr, App, tpl) {
        'use strict';

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        events: {
            "click #create-process"	: "createProcess"
        },

        createProcess: function(e) {
            e.preventDefault();
            var title = $('input[name=title]', this.el).val();
            var description = $('textarea[name=description]', this.el).val();
            if(title === "") {
                App.showNotification("Error", "You must provide at least a title", "error");
            } else {
                var processCollection = Worklr.processCollection;
                var newProcessModel = processCollection.create({ title:  title, description: description },
                    { success: function() {
                        App.router.navigate("processes", {trigger: true});
                }});
            }
        }
    });
});