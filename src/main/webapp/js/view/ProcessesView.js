var ProcessesView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['ProcessesView'], function() {
            that.$el.html(that.template());
        });
        return this;
    }

});