var HeaderView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['HeaderView'], function() {
            that.$el.html(that.template());
            $('.dropdown-toggle').dropdown();
        });        
        return this;
    },

    selectMenuItem: function (menuItem) {
        $('.nav li').removeClass('active');
        if (menuItem) {
            $('.' + menuItem).addClass('active');
        }
    }

});