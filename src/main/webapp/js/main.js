var AppRouter = Backbone.Router.extend({

    routes: {
    	""							: "showHome",
        "home"                      : "showHome",
		"login"         			: "login",
		"processes/create"      	: "createProcess",
		"processes"       			: "showProcesses",
		"processes/:id"       		: "showProcess",
		"profile/edit"          	: "editProfile",
		"folders/:folderName"    	: "showFolder",
		"requests/:requestId"    	: "showRequest",
		"logout"                	: "logout",
		"signup"                	: "signup"
    },

    initialize: function () {
    	console.log("Running initialize method...");
    },
    
    showHome: function() {
       	if(!localStorage["auth"]) {
       		this.login();
       	} else {
       		console.log("Loading Home View");
	        $('#app-wrapper').html(new HomeView().el);
        }
    },
    
    createProcess: function() {
    	var process = new ProcessModel();
        $('#content').html(new CreateProcessView({model: process}).el);
    },
    
    showProcesses: function() {
    	if(!this.processesView) {
    		this.processesView = new ProcessesView();
    	}
    	$('#content').html(this.processesView.el);
    },
    
    showProcess: function(id) {
        var process = new ProcessModel({id: id});
		process.fetch({
			success: function() {
            	$("#content").html(new ProcessView({model: process}).el);
            }
        });
    },
    
    editProfile: function() {
    	var profile = new ProfileModel();
		profile.fetch({
			success: function() {
            	$("#content").html(new EditProfileView({model: profile}).el);
            }
        });
    },
    
    showFolder: function(folderName) {
        this.showHome();
    },
    
    showRequest: function(requestId) {
		if (!this.requestView) {
            this.requestView = new RequestView();
        }
        $('#content').html(this.requestView.el);
    },
    
    login: function() {
        if(!this.loginView) {
            this.loginView = new LoginView();
        }
        $('#app-wrapper').html(this.loginView.el);
    },
    
    signup: function() {
        if(!this.signupView) {
            this.signupView = new SignupView();
        }
        $('#content').html(this.signupView.el);
    },
    
    logout: function() {
		localStorage.removeItem("auth");
        this.navigate("/", true);
    }
});

utils.loadTemplate([], function() {
    app = new AppRouter();
    Backbone.history.start();
});