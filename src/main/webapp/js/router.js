define([
    'jquery',
    'underscore',
    'mustache',
    'backbone',
    'marionette',
    'worklr',
    'app'
], function($, _, Mustache, Backbone, Marionette, Worklr, App) {

    return Backbone.Marionette.AppRouter.extend({

        initialize: function() {
            App.page.show(App.layouts.headerContentFooter);
        },

        appRoutes: {
            "" : "showInboxRequests",
            "processes/create" : "createNewProcess",
            "processes" : "showOngoingProcesses",
            "processes/ongoing" : "showOngoingProcesses",
            "processes/completed" : "showCompletedProcesses",
            "processes/:id" : "showProcess",
            "profile/edit" : "editProfile",
            "folders/inbox" : "showInboxRequests",
            "folders/executing" : "showExecutingRequests",
            "folders/sent" : "showSentRequests",
            "folders/completed" : "showCompletedRequests",
            "requests/:requestId" : "showRequest",
            "requests/:requestId/pending" : "showRequestPendingRequests",
            "users/:userId" : "showUser",
            "login" : "login",
            "logout" : "logout",
            "forbidden": "showForbidden"
        },

        controller: {

            createNewProcess : function() {
                var that = this;
                require(['views/CreateProcess'], function(CreateProcessView) {
                    App.layouts.headerContentFooter.contentRegion.show(new CreateProcessView());
                    that.selectMenuItem('create-process-menu');
                });
            },

            showProcessFolder: function(folderName) {
                App.page.show(App.layouts.headerContentFooter);
                App.layouts.headerContentFooter.contentRegion.show(App.layouts.folderList);
                require(['views/ProcessFolderMenu'], function(ProcessFolderMenuView) {
                    App.layouts.folderList.foldersRegion.show(new ProcessFolderMenuView());
                    App.layouts.folderList.foldersRegion.currentView.selectPill(folderName);
                });
            },

            showOngoingProcesses : function() {
                var that = this;
                this.showProcessFolder("ongoing");
                var processCollection = Worklr.processCollection;
                processCollection.url = "api/processes/ongoing";
                processCollection.fetch({
                    success : function() {
                        require(['views/OngoingProcessList'], function(OngoingProcessListView) {
                            App.layouts.folderList.listRegion.show(
                                new OngoingProcessListView({ collection : processCollection }));
                        });
                        that.selectMenuItem("processes-menu");
                    }
                });
            },

            showCompletedProcesses: function() {
                var that = this;
                this.showProcessFolder("completed");
                var processCollection = Worklr.processCollection;
                processCollection.url = "api/processes/completed";
                processCollection.fetch({
                    success : function() {
                        require(['views/CompletedProcessList'], function(CompletedProcessListView) {
                            App.layouts.folderList.listRegion.show(
                                new CompletedProcessListView({ collection : processCollection }));
                        });
                        that.selectMenuItem("processes-menu");
                    }
                });
            },

            showProcess : function(id) {
                var that = this;
                require(['models/Process', 'views/Process'], function(ProcessModel, ProcessView) {
                    var processModel = new ProcessModel({ id: id});
                    processModel.fetch({ success: function() {
                            App.layouts.headerContentFooter.contentRegion.show(
                                new ProcessView({ model : processModel }));
                    }});
                });

            },

            editProfile : function() {
                var that = this;
                require(['models/Profile', 'views/EditProfile'], function(ProfileModel, EditProfileView) {
                    var profileModel = new ProfileModel();
                    profileModel.fetch({
                        success : function() {
                            App.layouts.headerContentFooter.contentRegion.show(new EditProfileView({ model: profileModel }));
                            that.selectMenuItem(false);
                        }
                    });
                });

            },

            showFolder: function(folderName) {
                App.page.show(App.layouts.headerContentFooter);
                App.layouts.headerContentFooter.contentRegion.show(App.layouts.folderList);
                require(['views/FolderMenu'], function(FolderMenuView) {
                    App.layouts.folderList.foldersRegion.show(new FolderMenuView());
                    App.layouts.folderList.foldersRegion.currentView.selectPill(folderName);
                });
            },

            showInboxRequests : function() {
                this.showFolder("inbox");
                var that = this;
                require(['views/InboxRequestList'], function(InboxRequestListView) {
                    var inboxRequestCollection = Worklr.requestCollection;
                    inboxRequestCollection.url = "api/folders/inbox";
                    inboxRequestCollection.fetch({
                        success : function() {
                                App.layouts.folderList.listRegion.show(
                                    new InboxRequestListView({
                                        collection : inboxRequestCollection
                                    }));
                            that.selectMenuItem(false);
                        }
                    });
                });
            },

            showExecutingRequests : function() {
                this.showFolder("executing");
                var that = this;
                require(['views/ExecutingRequestList'], function(ExecutingRequestListView) {
                    var executingRequestCollection = Worklr.requestCollection;
                    executingRequestCollection.url = "api/folders/executing";
                    executingRequestCollection.fetch({
                        success : function() {
                                App.layouts.folderList.listRegion.show(
                                    new ExecutingRequestListView({
                                        collection : executingRequestCollection
                                    }));
                            that.selectMenuItem(false);
                        }
                    });
                });
            },

            showSentRequests : function() {
                this.showFolder("sent");
                var that = this;
                require(['views/SentRequestList'], function(SentRequestListView) {
                    var sentRequestCollection = Worklr.requestCollection;
                    sentRequestCollection.url = "api/folders/sent";
                    sentRequestCollection.fetch({
                        success : function() {
                            sentRequestCollection.sort();
                            App.layouts.folderList.listRegion.show(
                                new SentRequestListView({
                                    collection : sentRequestCollection
                                }));
                            that.selectMenuItem(false);
                        }
                    });
                });
            },

            showCompletedRequests : function() {
                this.showFolder("completed");
                var that = this;
                require(['views/CompletedRequestList'], function(CompletedRequestListView) {
                    var completedRequestCollection = Worklr.requestCollection;
                    completedRequestCollection.url = "api/folders/completed";
                    completedRequestCollection.fetch({
                        success : function() {
                            App.layouts.folderList.listRegion.show(
                                new CompletedRequestListView({
                                    collection : completedRequestCollection
                            }));
                            that.selectMenuItem(false);
                        }
                    });
                });
            },

            showRequest : function(requestId) {
                require([
                    'models/Request',
                    'views/ExecutingRequest',
                    'views/UnclaimedRequest',
                    'views/CompletedRequest'], function(RequestModel, ExecutingRequestView, UnclaimedRequestView, CompletedRequestView) {
                    var requestModel = new RequestModel();
                    requestModel.id = requestId;
                    requestModel.fetch({
                        success : function() {
                            if(requestModel.hasInitiator(Worklr.profileModel.get("id"))) {
                                var parentId = requestModel.get("parentId");
                                console.log("Olho parent: "+parentId);
                                if(parentId) {
                                    App.router.navigate("requests/"+parentId, { trigger: true });
                                } else {
                                    App.layouts.headerContentFooter.contentRegion.show(new ExecutingRequestView({ model: requestModel }));
                                }
                            } else if(requestModel.isCompleted()) {
                                App.layouts.headerContentFooter.contentRegion.show(new CompletedRequestView({ model: requestModel }));
                            } else if(requestModel.isUnclaimed()) {
                                App.layouts.headerContentFooter.contentRegion.show(new UnclaimedRequestView({ model: requestModel }));
                            } else if(requestModel.hasExecutor(Worklr.profileModel.get("id"))) {
                                App.layouts.headerContentFooter.contentRegion.show(new ExecutingRequestView({ model: requestModel }));
                            } else {
                                App.router.navigate("forbidden", { trigger: true });
                            }
                    }});
                });
            },

            showUser: function(userId) {
                require(['models/User', 'views/User'], function(UserModel, UserView) {
                    var userModel = new UserModel();
                    userModel.id = userId;
                    userModel.fetch({
                        success : function() {
                            App.layouts.headerContentFooter.contentRegion.show(new UserView({ model: userModel }));
                        }
                    });
                });
            },

            showRequestPendingRequests: function(requestId) {
                var pendingRequestCollection = Worklr.RequestCollection;
                pendingRequestCollection.url = "api/requests/"+requestId+"/pending";
                pendingRequestCollection.fetch({
                    success: function() {
                        require(['views/PendingRequestList'], function(PendingRequestListView) {
                            console.log(pendingRequestCollection.toJSON());
                            App.layout.contentRegion.show(new PendingRequestListView({ collection: pendingRequestCollection }));
                        });
                    }
                });
            },

            login : function() {
                if (localStorage["auth"]) {
                    localStorage.removeItem("auth");
                }
                require(['views/Login'], function(LoginView) {
                    App.page.show(new LoginView());
                });
            },

            logout : function() {
                localStorage.removeItem("auth");
                App.showNotification("Info", "Logout successful", "info");
                App.router.navigate("login", { trigger: true });

            },

            showForbidden: function() {
                require(['views/Forbidden'], function(ForbiddenView) {
                    App.layouts.headerContentFooter.contentRegion.show(new ForbiddenView());
                });
            },

            selectMenuItem : function(menuItem) {
                $('.menu-item').removeClass('active');
                if (menuItem) {
                    $('.' + menuItem).addClass('active');
                }
            }
        }

    });
});
