var ProcessView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['ProcessView'], function() {
            that.$el.html(that.template(that.model.toJSON()));
        });
        return this;
    }

});