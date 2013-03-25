define([
    'backbone',
    'marionette',
    'worklr',
    'views/Header',
    'views/Footer',
    'text!layoutTemplates/HeaderContentFooter.html'
], function(Backbone, Marionette, Worklr, HeaderView, FooterView, tpl) {

    return Backbone.Marionette.Layout.extend({

        template: tpl,

        regions: {
            headerRegion: "#header",
            contentRegion: "#content",
            footerRegion: "#footer",
            modalRegion: "#modal-placeholder"
        },

        onShow: function() {
            var that = this;
            var profileModel = Worklr.profileModel;
            profileModel.fetch({
                success: function() {
                    that.headerRegion.show(new HeaderView({ model: profileModel }));
                }
            })
            that.footerRegion.show(new FooterView());
        }
    });
});