require([
    'jquery',
    'jquery.bootstrap',
    'backbone',
    'mustache',
    'marionette',
    'worklr',
    'app',
    'layouts/HeaderContentFooter',
    'layouts/FolderList',
    'i18n!nls/worklr'
], function($, jQueryBootstrap, Backbone, Mustache, Marionette, Worklr, App, HeaderContentFooterLayout, FolderListLayout, i18n) {

    $.ajaxSetup({
        contentType: "application/json; charset=utf-8",
        statusCode : {
            401 : function() {
                // Redirect the to the login page.
                App.router.navigate("login", true);
            },
            403 : function() {
                // 403 -- Access denied
                App.router.navigate("login", true);
            }
        }
    });

    Backbone.ajaxSync = Backbone.sync;

    Backbone.customSync = function(method, model, option) {
        option.beforeSend = function(jqXHR) {
            if (localStorage.getItem("auth") != null) {
                var auth = localStorage["auth"];
                jqXHR.setRequestHeader('Authorization', 'Basic ' + auth);
            }
        };
        return Backbone.ajaxSync(method, model, option);
    }

    Backbone.sync = Backbone.customSync;

    Backbone.Marionette.Renderer.render = function(template, data) {
        data['_abv'] = function() {
            return function(val) {
                var maxLength = 15;
                var text = this[val];
                if(text.length > maxLength) {
                    return this[val].substring(0,maxLength)+"...";
                }
                return text;
            }
        };
        data['_i18n'] = function() {
            return function(val) {
                return i18n[val];
            }
        }
        data['_noti18n'] = function() {
            return function(val) {
                return i18n[data[val]];
            }
        }
        return Mustache.to_html(template, data);
    }

    App.addRegions({
        notification: "#notification",
        page: "#page"
    });


    App.showNotification = function(title, message, type) {
        require(['views/Notification'], function(NotificationView) {
            App.notification.show(new NotificationView({ type: type, title: title, message: message }));
        });
    };

    App.addInitializer(function() {
        App.layouts = {};
        require(['layouts/HeaderContentFooter', 'layouts/FolderList', 'router'], function(HeaderContentFooterLayout, FolderListLayout, Router) {
            App.layouts.headerContentFooter = new HeaderContentFooterLayout();
            App.layouts.folderList = new FolderListLayout();
            App.router = new Router();
            Backbone.history.start();
        });
    });

    App.start();

});
