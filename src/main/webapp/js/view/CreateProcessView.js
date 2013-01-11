var CreateProcessView = Backbone.View.extend({

    initialize: function () {
    	this.render();
    	this.listenTo(this.model, "change", this.render);
    },

    render: function () {
        var that = this;
        utils.loadTemplate(['CreateProcessView'], function() {
            that.$el.html(that.template(that.model.toJSON()));
        });
        return this;
    },
    
	events: {
	   	"change"				: "change",
        "click #create-process"	: "createNewProcess"
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
    
    createNewProcess: function() {
		utils.showAlert("Process Created with Success", "Process created successfully!", "alert-info");
    }

});