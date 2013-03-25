define([
    'jquery',
    'mustache',
    'backbone',
    'app',
    'router',
    'worklr',
    'views/Header',
    'text!templates/Home.html'
], function($, Mustache, Backbone, App, Router, Worklr, HeaderView, tpl) {

    return Backbone.Marionette.View.extend({

        template: tpl,

        render : function() {
            var that = this;
            var profileModel = Worklr.ProfileModel;
            profileModel.fetch({ success: function() {
                App.appLayout.headerRegion.show(new HeaderView({ model: profileModel }));
                that.$el.html(Mustache.to_html(this.template));
            }});
            return this;
        }

    });
});