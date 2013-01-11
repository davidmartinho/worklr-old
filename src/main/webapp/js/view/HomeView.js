var HomeView = Backbone.View.extend({

    initialize: function () {
        this.render();        
    },

    render: function () {
    	console.log("Rendering HomeView");
        var that = this;
        utils.loadTemplate(['HomeView'], function() {
            that.$el.html(that.template());
            if(!that.headerView) {
            	that.headerView = new HeaderView();
            }
            $('#header').html(that.headerView.el);
             if(!that.footerView) {
            	that.footerView = new FooterView();
            }
        	$('#footer').html(that.footerView.el);
        });
        return this;
    },
    
    selectMenuItem: function (menuItem) {
        $('.nav-pills li').removeClass('active');
        if (menuItem) {
            $('.folder-' + menuItem + '-menu').addClass('active');
        }
    }

});