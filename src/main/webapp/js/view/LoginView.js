var LoginView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['LoginView'], function() {
            that.$el.html(that.template());
        });
        return this;
    },
    
    events: {
    	"click .login-button": "login"
    },
    
    login: function(event) {
    	event.preventDefault();
    	var email = $('input[name=email]', this.el).val();
    	var password = $('input[name=password]', this.el).val();
    	this.loginModel = new LoginModel({'email': email, 'password': password});
    	this.loginModel.save(null, {success: function(model, response) {
			var auth = $.base64.encode(response.userId+':'+response.token);
    		localStorage["auth"] = auth;
	    	app.showHome();
    	}});
    }

});