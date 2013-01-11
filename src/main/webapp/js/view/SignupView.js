var SignupView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    events: {
        "submit form#signupForm": "submitForm",
    },

    submitForm: function(event){
        event.preventDefault();
        name = $('#inputName').val();
        alert(name);
        return false;
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['SignupView'], function() {
            that.$el.html(that.template());
        });
        return this;
    }

});