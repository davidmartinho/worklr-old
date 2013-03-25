define([
    'jquery',
    'marionette',
    'app',
    'text!templates/EditProfile.html'
], function($, Marionette, App, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        events: {
            "click #save-profile": "saveProfile"
        },

        saveProfile: function(e) {
            e.preventDefault();
            var name = $('input[name=name]', this.el).val();
            var email = $('input[name=email]', this.el).val();
            if(name === "") {
                App.showNotification("Error", "The name cannot be empty", "error");
            } else if(email === "") {
                App.showNotification("Error", "The email cannot be empty", "error");
            } else {
                this.model.save({ name: name, email: email }, {
                    success: function(model) {
                        App.router.navigate("profile/edit", true);
                    }
                });
            }
        }
    });
});