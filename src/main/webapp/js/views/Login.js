define([
    'jquery',
    'mustache',
    'backbone',
    'app',
    'router',
    'worklr',
    'models/Login',
    'text!templates/Login.html'
], function($, Mustache, Backbone, App, Router, Worklr, LoginModel, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #login-button": "login"
        },

        login: function(e) {
            e.preventDefault();
            var email = $('input[name=email]', this.el).val();
            var password = $('input[name=password]', this.el).val();
            if(email === "" || password === "") {
                App.showNotification("Warning", "Email and password cannot be empty", "warn");
            } else {
                var loginModel = new LoginModel();
                loginModel.save({ email: email, password: password }, {
                    success: function(model, response) {
                        var auth = btoa(response.userId+':'+response.token);
                        localStorage["auth"] = auth;
                        App.router.navigate("", { trigger: true} );
                        App.showNotification("Info", "Login successful", "info");
                    },
                    error: function(model, response) {
                        var msg = JSON.parse(response.responseText);
                        App.showNotification("Error", msg.message, "error");
                    }
                });
            }
        }
    });
});
