var RequestView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['RequestView'], function() {
            that.$el.html(that.template());
        });
        return this;
    }

});