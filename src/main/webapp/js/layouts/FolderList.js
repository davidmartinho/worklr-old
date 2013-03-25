define([
    'backbone',
    'marionette',
    'views/FolderMenu',
    'text!layoutTemplates/FolderList.html'
], function(Backbone, Marionette, FolderMenuView, tpl) {

    return Backbone.Marionette.Layout.extend({

        template: tpl,

        regions: {
            foldersRegion: "#folders",
            listRegion: "#list"
        }
    });
});