var FolderModel = Backbone.Model.extend({

    initialize: function() {
        this.requestCollection = new RequestCollection(this.get('requestCollection'), { folder: this });
        this.requestCollection.bind('change', this.save);
    },

    defaults: {
        id: null,
        folderName: "Folder"
    }
});