var FooterView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['FooterView'], function() {
            that.$el.html(that.template());
        });
        return this;
    }

});