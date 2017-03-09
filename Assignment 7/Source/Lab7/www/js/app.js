// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'

angular.module('QRCode', [])
    .controller('main', function ($scope, $http) {
        $scope.idno=document.getElementById('text');


        $scope.dataQR = function() {
            document.getElementById("displayhere").src = "https://api.qrserver.com/v1/create-qr-code/?data="+document.getElementById("text").value +"&amp;size=100x100" ;
        }





    });


//UStream JavaScript

var UstreamEmbed = (function () {

    var objectKeys = (typeof Object.keys !== 'undefined'),
        instances = {},
        hostRegexp = new RegExp('^(http(?:s)?\://[^/]+)', 'im');

    function UstreamEmbed (iframe) {
        return createInstance(iframe);
    }

    function createInstance (iframe) {
        var element = getIframe(iframe),
            instance = (function (element) {
                var isReady = false,
                    instanceObj,
                    embedHost,
                    sStreamConnected = false,
                    sStreamHost,
                    sStreamElement,
                    cmdQueue = [],
                    getters = {},
                    events = {},
                    ieHackEvent = [];

                embedHost = getHostName(element.getAttribute('src')).toLowerCase();

                element.onload = onLoadElement;

                function addCommandQueue (method) {

                    if (method === 'socialstream') {
                        addDomEvent(window, 'message', onSocialFrame);

                        // social stream connect
                        sStreamElement = getIframe(arguments[1]);

                        sStreamHost = getHostName(sStreamElement.getAttribute('src'));
                        sStreamConnected = true;

                        if (ieHackEvent.length) {
                            for (var i = 0, il = ieHackEvent.length; i < il; i++) {
                                onMessage(ieHackEvent[i]);
                            }
                        }
                        return;
                    }

                    if (!isReady) {
                        if (!cmdQueue) {
                            cmdQueue = [];
                        }
                        cmdQueue.push(arguments);
                        return;
                    }

                    var args = (makeArray(arguments)).slice(1);

                    if (args[0] && typeof args[0] === 'function') {
                        // getter callback
                        if (!getters[method]) {
                            getters[method] = [];
                        }
                        getters[method].push(args[0]);
                    }

                    sendMessage(element, embedHost, {cmd: method, args: args});
                }

                function execCommandQueue () {
                    if (cmdQueue) {
                        while (cmdQueue.length) {
                            addCommandQueue.apply(this, cmdQueue.shift());
                        }
                        cmdQueue = null;
                    }
                }

                function onSocialFrame (e) {
                    var doc = sStreamElement;

                    if (doc && doc.contentWindow && doc.contentWindow === e.source) {
                        instanceObj.onmessage(e);
                    } else if (e.source === sStreamElement.id) {
                        instanceObj.onmessage(e);
                    }
                }

                function onSStreamMsg (e) {
                    var d = JSON.parse(e.data),
                        args;

                    if (!!d.cmd && d.cmd == 'ready') {
                        // handshake
                        sendMessage(sStreamElement, sStreamHost, {cmd: 'ready'});
                        return;
                    }

                    args = [d.cmd];
                    args = args.concat(d.args);

                    addCommandQueue.apply(this, args);
                }

                function onLoadElement () {
                    sendMessage(element, embedHost, {cmd: 'ready'});
                }

                function ready () {
                    isReady = true;
                    sendMessage(element, embedHost, {cmd: 'apihandshake', args: []});
                    execCommandQueue();

                    if (sStreamElement) {
                        sendMessage(sStreamElement, sStreamHost, {cmd: 'viewer_ready'});
                    }
                }

                function callMethod () {
                    addCommandQueue.apply(this, arguments);
                }

                return instanceObj = {
                    host: embedHost,
                    callMethod: callMethod,

                    getProperty: function () {
                        callMethod.apply(this,arguments);
                    },

                    addListener: function (event, callback) {
                        if (!events[event]) {
                            events[event] = [];
                        }
                        events[event].push(callback);
                    },

                    removeListener: function (event, callback) {
                        if (callback) {
                            // we miss u "array.indexOf" in old IE :(
                            for (var i = 0, eL = events[event].length; i < eL ; i++ ) {
                                if (events[event][i] === callback) {
                                    events[event].splice(i, 1);
                                }
                            }
                        } else {
                            events[event] = null;
                        }
                    },

                    onmessage: function (e) {
                        var d;

                        if (!embedHost && !sStreamHost) {
                            // Combined embed IE8-ban csinalhat olyat, hogy
                            // mindket embed iframe kilovi a ready-t, de a Ustream Embed meg nem
                            // peldanyosodott, igy nincs iframe URL sehol. (embedhost, sstreamhost stb.)
                            // ugyh itt rogzitjuk a megnem hallott eventeket
                            // aztan amikor megvolt az init, akkor behivunk megint ide...
                            ieHackEvent.push({
                                origin: e.origin, // string
                                data: e.data //string
                            });
                            // innen nem futunk bele semmibe :(
                            // ugyh ha a ss inicializalas megvan visszahivunk ide
                        }

                        if (e.origin.toLowerCase() == embedHost) {
                            try {
                                d = JSON.parse(e.data);
                            } catch (err) {
                                return;
                            }

                            if (d.sstream) {
                                onSStreamMsg(e);
                                return;
                            }

                            if (!!d.event && d.event.ready) {
                                ready();
                                dispatchEvent(events, 'ready');
                            }

                            if (!!d.event && d.event.live === true) {
                                dispatchEvent(events, 'live');
                                return;
                            } else if (!!d.event && d.event.live === false) {
                                dispatchEvent(events, 'offline');
                                return;
                            }

                            if (!!d.event && !d.event.ready) {
                                if (objectKeys) {
                                    Object.keys(d.event).forEach(function (key) {
                                        dispatchEvent(events, key, d.event[key]);
                                    });
                                } else {
                                    for (var key in d.event) {
                                        if (d.event.hasOwnProperty(key)) {
                                            dispatchEvent(events, key, d.event[key]);
                                        }
                                    }
                                }
                            }

                            // minden mas esetben
                            if (!!d.property) {
                                if (objectKeys) {
                                    Object.keys(d.property).forEach(function (key) {
                                        callGetter(getters, key, d.property[key]);
                                    });
                                } else {
                                    for (var key in d.property) {
                                        if (d.property.hasOwnProperty(key)) {
                                            callGetter(getters, key, d.property[key]);
                                        }
                                    }
                                }
                            }

                        } else if (sStreamConnected && e.origin == sStreamHost) {
                            onSStreamMsg(e);
                            return;
                        }
                    },

                    destroy: function () {
                        isReady = false;
                        embedHost = '';
                        sStreamConnected = false;
                        sStreamHost = '';
                        sStreamElement = null;
                        cmdQueue = [];
                        getters = {};
                        events = {};
                        ieHackEvent = [];

                        if (instances[element.id]) instances[element.id] = null;
                        element = null;
                    }
                };
            }(element));

        if (!element.id) {
            element.id = 'UstreamEmbed' + Math.ceil(Math.random() * 100000);
        }

        instance.id = element.id;

        instances[element.id] = instance;
        return instance;
    }

    function getIframe (iframe) {
        if (typeof iframe === 'string') {
            iframe = document.getElementById(iframe);
        }
        return iframe;
    }

    function dispatchEvent (events, event, data) {
        for (var cb in events[event]) {
            if (events[event].hasOwnProperty(cb)) {
                events[event][cb].call(window, event, data);
            }
        }
    }

    function callGetter (getters, event, data) {
        if (!getters[event]) {
            return;
        }

        // keep reference to array only here
        var items = getters[event];

        // then delete the original array
        getters[event] = null;
        delete getters[event];

        items.forEach(function (item) {
            item.call(window, data);
        });
    }

    function onMessage (e) {
        var ins, doc;
        for (ins in instances) {
            if (instances.hasOwnProperty(ins) && instances[ins]) {
                doc = document.getElementById(ins);
                if (doc && doc.contentWindow) {
                    if (doc.contentWindow === e.source) {
                        instances[ins].onmessage(e);
                    }
                } else if (typeof e.source  === 'string' && e.source == ins) {
                    instances[ins].onmessage(e);
                }
            }
        }
    }

    function sendMessage (element, host, data) {
        element.contentWindow.postMessage(JSON.stringify(data), host);
    }

    function getHostName (url) {
        if (url.indexOf('http') < 0) {
            url = location.protocol + url;
        }
        return url.match(hostRegexp)[1].toString();
    }

    function makeArray (smtg) {
        return Array.prototype.slice.call(smtg, 0);
    }

    function addDomEvent (target, event, cb) {
        if (target.addEventListener) {
            target.addEventListener(event, cb, false);
        } else {
            target.attachEvent('on' + event, cb);
        }
    }

    addDomEvent(window, 'message', onMessage);

    if (typeof define === 'function' && define.amd) {
        define([], function () {
            return UstreamEmbed;
        });
    }

    return (window.UstreamEmbed = UstreamEmbed);

})();




$(document).ready(function () {

    var viewer = UstreamEmbed("UstreamFrame");

    $('.media').on("click", function (e) {
        clearFields();

        var target = $(e.currentTarget),
            media = target.data('media'),
            type = target.data('type');

        viewer.callMethod('load', type, media);

        e.preventDefault();
    });


    /*
     set up controls
     */
    $(document.body).on("click", '.btn, .dropdown-menu a', function (e) {
        var target = $(e.currentTarget),
            control = target[0].className.match(/control-([a-zA-Z0-9\-]*)/);

        if (control && control[1]) {

            switch (control[1]) {

                case "play":
                    viewer.callMethod('play');
                    break;
                case "pause":
                    viewer.callMethod('pause');
                    break;
                case "seek":
                    viewer.callMethod('seek', $("#SeekTo").val());
                    break;
                case "volume":
                    viewer.callMethod('volume', $("#SetVolume").val());
                    break;
                case "duration":
                    viewer.getProperty('duration', function (data) {
                        $('#Duration').val(data);
                    });
                    break;
                case "progress":
                    viewer.getProperty('progress', function (data) {
                        $('#Progress').val(data);
                    });
                    break;
                case "viewers":
                    viewer.getProperty('viewers', function (data) {
                        $('#Viewers').val(data);
                    });
                    break;
                case "content":
                    viewer.getProperty('content', function (data) {
                        $('#Content').val(data.join(','));
                    });
                    break;
                case "load-video":
                    clearFields();
                    viewer.callMethod('load', 'video', $("#LoadContent").val());
                    break;
                case "load-channel":
                    clearFields();
                    viewer.callMethod('load', 'channel', $("#LoadContent").val());
                    break;
                case "quality":
                    viewer.callMethod('quality', target.data('quality'));
                    break;

            }

        }

        e.preventDefault();
    });


    /*
     populate quality selector with receiced data
     */
    var populateQualitySelector = function (data) {
        var list =  $('.quality-selector');
        list.find('li').remove(); // remove previously set data
        for (var id in data) {
            list.append('<li><a href="#" class="control-quality" data-quality="' + id + '">' + data[id].label + '</a></li>')
        }
    }

    /*
     clear some data fields that are relevant to content
     */
    var clearFields = function () {
        populateQualitySelector({});
        $('#Progress').val('');
        $('#Viewers').val('');
        $('#Duration').val('');

        $('.st-ended').hide();
        $('.st-playing').hide();
        $('.st-offline').hide();
        $('.st-live').hide();
    }

    /*
     event handler for events received from the embed iframe
     */
    var onEmbedEvent = function (event, data) {
        switch (event) {
            case "ready":
                clearFields();
                break;
            case "quality":
                populateQualitySelector(data);
                break;
            case "live":
                $('.st-live').show();
                $('.st-offline').hide();
                break;
            case "offline":
                $('.st-offline').show();
                $('.st-live').hide();
                break;
            case "playing":
                if (data) {
                    $('.st-playing').show()
                } else {
                    $('.st-playing').hide();
                }
                break;
            case "finished":
                $('.st-ended').show();
                break;
            case "content":
                $('.st-content').text(data.join(','));
                break;
            case "size":
                break;
        }
    }

    viewer.addListener('ready', onEmbedEvent);
    viewer.addListener('quality', onEmbedEvent);
    viewer.addListener('live', onEmbedEvent);
    viewer.addListener('offline', onEmbedEvent);
    viewer.addListener('playing', onEmbedEvent);
    viewer.addListener('finished', onEmbedEvent);
    viewer.addListener('size', onEmbedEvent);
    viewer.addListener('content', onEmbedEvent);

});




angular.module('starter', ['ionic'])
.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
