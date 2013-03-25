define([
    'collections/User',
    'collections/Queue',
    'collections/Process',
    'collections/Request',
    'models/Profile',
    'models/Login',
    'views/FolderMenu'
], function(UserCollection, QueueCollection, ProcessCollection, RequestCollection, ProfileModel, LoginModel, FolderMenuView) {

    var Worklr = Worklr || {};

    //Collections
    Worklr.userCollection = Worklr.userCollection || new UserCollection();
    Worklr.queueCollection = Worklr.queueCollection || new QueueCollection();
    Worklr.processCollection = Worklr.processCollection || new ProcessCollection();
    Worklr.requestCollection = Worklr.requestCollection || new RequestCollection();

    //Models
    Worklr.profileModel = Worklr.profileModel || new ProfileModel();
    Worklr.loginModel = Worklr.loginModel || new LoginModel();

    //Views
    Worklr.FolderMenuView = Worklr.FolderMenuView || FolderMenuView;

    Worklr.parseTimestamps = function() {
        require(['moment', 'moment.pt'], function(moment, momentPT) {
            moment.lang(require.s.contexts._.config.config.i18n.locale.substring(0,2));
            $('.timestamp').each(function() {
                if($(this).text()) {
                    $(this).text(moment($(this).text()).fromNow());
                }
            });
            $('.interval').each(function() {
                if($(this).text()) {
                    var text = $(this).text();
                    var tokens = text.split(",");
                    var start = moment(tokens[0]);
                    var end = moment(tokens[1]);
                    $(this).text(start.from(end, true));
                }
            });
        });
    };

    return Worklr;

});

