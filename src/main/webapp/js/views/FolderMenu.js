define([
    'jquery',
    'backbone',
    'marionette',
    'text!templates/FolderMenu.html'
], function($, Backbone, Marionette, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        deselectAllPills: function() {
            $('.folder-filter-pill').removeClass("active");
        },

        selectPill: function(folder) {
            this.deselectAllPills();
            $("#"+folder+"-folder").addClass("active");
        }

    });
});