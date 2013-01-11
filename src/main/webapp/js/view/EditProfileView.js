var EditProfileView = Backbone.View.extend({

    initialize: function () {
		this.render();
    },
    
    events: {
    	"change"				: "change",
        "click .save-changes"   : "saveChanges"
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['EditProfileView'], function() {
            that.$el.html(that.template(that.model.toJSON()));
        });
        return this;
    },
    
    change: function (event) {
        // Remove any existing alert message
        utils.hideAlert();


        // Apply the change to the model
        var target = event.target;

        var change = {};
        change[target.name] = target.value;
        this.model.set(change);

    },
    
    saveChanges: function() {
    	var that = this;
    	this.model.save(null, {
            success: function (model) {
                app.navigate('profile/edit', { trigger: true });
                utils.showAlert('Success!', 'Profile saved successfully', 'alert-success');
            },
            error: function () {
                utils.showAlert('Error', 'An error occurred while trying to update your profile', 'alert-error');
            }
        });
    }

});