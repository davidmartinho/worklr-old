require.config({

    paths: {
        'jquery': 'libs/jquery/jquery',
        'jquery.ui': 'libs/jquery/jquery.ui-min',
        'jquery.tokeninput': 'libs/jquery/jquery.tokeninput',
        'less': 'libs/less/less-min',
        'moment': 'libs/moment/moment-min',
        'moment.pt': 'libs/moment/lang/pt',
        'underscore': 'libs/underscore/underscore-min',
        'mustache': 'libs/mustache/mustache-min',
        'backbone': 'libs/backbone/backbone',
        'marionette': 'libs/backbone/backbone.marionette',
        'paginator': 'libs/backbone/backbone.paginator',
        'jquery.bootstrap': 'libs/bootstrap/bootstrap-min',
        'text': 'libs/require/text',
        'i18n': 'libs/require/i18n',
        'templates': '../templates',
        'layoutTemplates': '../templates/layouts'
    },

    config: {
        //Set the config for the i18n
        //module ID
        i18n: {
            locale: 'pt-pt'
        }
    },

    shim: {
        'moment': {
            exports: 'moment'
        },
        'underscore': {
            exports: '_'
        },
        'jquery.ui': {
            deps: ['jquery'],
            exports: 'jquery'
        },
        'jquery.bootstrap': {
            deps: ['jquery'],
            exports: 'jquery'
        },
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'marionette': {
            deps: ['backbone'],
            exports: 'Backbone.Marionette'
        },
        'paginator': {
            deps: ['backbone'],
            exports: 'Backbone.Paginator'
        }
    },

    deps: ['main']
});
