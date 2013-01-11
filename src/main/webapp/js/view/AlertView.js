var AlertView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['AlertView'], function() {
            that.$el.html(that.template());
        });
        return this;
    }

});