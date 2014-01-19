/**
 * Setup the webStorageModule.
 */
var webStorageModule = angular.module('webStorageModule', []);

/**
 * Set a prefix that will be used for all storage data (defaults to the empty string.)
 */
webStorageModule.constant('prefix', '');

/**
 * The order in which the service selects what storage model to use. Note that the module
 * mimics localStorage and sessionStorage by using cookies if one, or both, of the web storage 
 * models aren't supported.
 */
webStorageModule.constant('order', ['local', 'session', 'memory']);

/**
 * Name of the error that will be broadcast via the $rootScope on errors. 
 */
webStorageModule.constant('errorName', 'webStorage.notification.error');

/**
 * Setup the webStorage service.
 */
webStorageModule.factory('webStorage', ['$rootScope', 'prefix', 'order', 'errorName', function($rootScope, prefix, order, errorName) {
        /**
         * Boolean flag indicating client support for local storage.
         * @private 
         */
        var hasLocalStorage = testLocalStorage();

        /**
         * Boolean flag indicating client support for session storage.
         * @private 
         */
        var hasSessionStorage = testSessionStorage();
        
        /**
         * In-memory object used as last resort if no web storage engine is supported by the client.
         * @private
         */
        var ram = {};
        
        /**
         * The webStorage service API.
         */
        var webStorage = {
                /** Boolean flag indicating that the client has support for some form of web storage or not. */
                isSupported: hasLocalStorage || hasSessionStorage,
                
                /** 
                 * The local storage API.
                 * The API is the same as the generic API for the webStore service, but will
                 * only operate directly on the local store. Errors will be broadcast via
                 * the $rootScope.
                 */
                local: {
                        isSupported: hasLocalStorage,
                        add: addToLocal,
                        get: getFromLocal,
                        remove: removeFromLocal,
                        clear: clearLocal
                },
                
                /**
                 * The session storage API.
                 * The API is the same as the generic API for the webStore service, but will
                 * only operate directly on the session store. Errors will be broadcast via
                 * the $rootScope.
                 */
                session: {
                        isSupported: hasSessionStorage,
                        add: addToSession,
                        get: getFromSession,
                        remove: removeFromSession,
                        clear: clearSession
                },
                
                /**
                 * The in-memory API.
                 * The API is the same as the generic API for the webStore service, but will
                 * only operate directly on the in-memory store. Errors will be broadcast via
                 * the $rootScope.
                 */
                memory: {
                        isSupported: true,
                        add: addToMemory,
                        get: getFromMemory,
                        remove: removeFromMemory,
                        clear: clearMemory
                }
        };
        
        /**
         * Setter for the key/value web store.
         * 
         * NOTE: The method will use local or session storage depending on the client's 
         * support as well as the order set in the module constant 'order'.
         * 
         * @param {String} key Name to store the given value under.
         * @param {mixed} value The value to store.
         * @return {boolean} True on success, else false.
         */
        webStorage.add = function(key, value) {
                var length = order.length;
                for (var ith = 0; ith < length; ++ith) {
                        var engine = webStorage[order[ith]];
                        if (engine.isSupported)
                                return engine.add(key, value);
                }
                return false;
        };
        
        /**
         * Getter for the key/value web store.
         * 
         * NOTE: The method will use local or session storage depending on the client's 
         * support as well as the order set in the module constant 'order'.
         * 
         * @param {String} key Name of the value to retrieve.
         * @return {mixed} The value previously added under the specified key, else null.
         */
        webStorage.get = function(key) {
                var length = order.length;
                for (var ith = 0; ith < length; ++ith) {
                        var engine = webStorage[order[ith]];
                        if (engine.isSupported)
                                return engine.get(key);
                }
                return null;
        };

        /**
         * Remove values from the key/value web store.
         * 
         * NOTE: The method will use local or session storage depending on the client's 
         * support as well as the order set in the module constant 'order'.
         * 
         * @param {String} key Name of the value to remove.
         * @return {boolean} True on success, else false.
         */
        webStorage.remove = function(key) {
                var length = order.length;
                for (var ith = 0; ith < length; ++ith) {
                        var engine = webStorage[order[ith]];
                        if (engine.isSupported)
                                return engine.remove(key);
                }
                return false;
        };

        /**
         * Remove all values in the key/value web store.
         * 
         * If a prefix has been specified in the module constant 'prefix' then
         * only values with that specific prefix will be removed.
         * 
         * NOTE: The method will use local or session storage depending on the client's 
         * support as well as the order set in the module constant 'order'.
         * 
         * @return {boolean} True on success, else false.
         */
        webStorage.clear = function() {
                var length = order.length;
                for (var ith = 0; ith < length; ++ith) {
                        var engine = webStorage[order[ith]];
                        if (engine.isSupported)
                                return engine.clear();
                }
                return false;
        };
        
        /**
         * Add the specified key/value pair to the local web store.
         * 
         * NOTE: The web store API only specifies that implementations should be able to
         * handle string values, this method will therefore stringify all values into 
         * JSON strings before storing them.
         * 
         * @param {String} key The name to store the value under.
         * @param {mixed} value The value to set (all values are stored as JSON.)
         * @return {boolean} True on success, else false.
         * @private
         */
        function addToLocal(key, value) {
                if (hasLocalStorage) {
                        try { localStorage.setItem(prefix + key, JSON.stringify(value)); } catch (e) { return croak(e); }
                        return true;
                }
                return false;
        };
        
        /**
         * Add the specified key/value pair to the session web store.
         * 
         * NOTE: The web store API only specifies that implementations should be able to
         * handle string values, this method will therefore stringify all values into 
         * JSON strings before storing them.
         * 
         * @param {String} key The name to store the value under.
         * @param {mixed} value The value to set (all values are stored as JSON.)
         * @return {boolean} True on success, else false.
         * @private
         */
        function addToSession(key, value) {
                if (hasSessionStorage) {
                        try { sessionStorage.setItem(prefix + key, JSON.stringify(value)); } catch (e) { return croak(e); }
                        return true;
                }
                return false;
        };
        
        /**
         * Add the specified key/value pair to the in-memory store.
         * 
         * NOTE: The in-memory storage does not use prefixes.
         * 
         * @param {String} key The name to store the value under.
         * @param {mixed} value The value to set.
         * @return {boolean} True on success, else false.
         * @private
         */
        function addToMemory(key, value) {
                ram[key] = value;
                return true;
        }
        
        /**
         * Get the specified value from the local web store.
         * 
         * NOTE: Since all values are stored as JSON strings, this method will parse the fetched
         * JSON string and return the resulting object/value.
         * 
         * @param {String} key The name of the value.
         * @return {mixed} The value previously added under the specified key, else null.
         * @private
         */
        function getFromLocal(key) {
                if (hasLocalStorage) {
                        try { 
                                var value = localStorage.getItem(prefix + key);
                                return value && JSON.parse(value); 
                        } catch (e) { return croak(e); }                        
                }
                return null;
        };
        
        /**
         * Get the specified value from the session web store.
         * 
         * NOTE: Since all values are stored as JSON strings, this method will parse the fetched
         * JSON string and return the resulting object/value.
         * 
         * @param {String} key The name of the value.
         * @return {mixed} The value previously added under the specified key, else null.
         * @private
         */
        function getFromSession(key) {
                if (hasSessionStorage) {
                        try {
                                var value = sessionStorage.getItem(prefix + key);
                                return value && JSON.parse(value); 
                        } catch (e) { return croak(e); }
                }
                return null;
        };
        
        /**
         * Get the specified value from the in-memory store.
         * 
         * NOTE: The in-memory storage does not use prefixes.
         * 
         * @param {String} key The name of the value.
         * @return {mixed} The value previously added under the specified key, else null.
         * @private
         */
        function getFromMemory(key) {
                return key in ram ? ram[key] : null;
        }
        
        /**
         * Remove the specified key/value pair from the local store.
         * 
         * @param {String} key The name of the value to remove.
         * @return {boolean} True on success, else false.
         * @private
         */
        function removeFromLocal(key) {
                if (hasLocalStorage) {
                        try { localStorage.removeItem(prefix + key); } catch (e) { return croak(e); }
                        return true;                        
                }
                return false;
        };
        
        /**
         * Remove the specified key/value pair from the session store.
         * 
         * @param {String} key The name of the value to remove.
         * @return {boolean} True on success, else false.
         * @private
         */
        function removeFromSession(key) {
                if (hasSessionStorage) {
                        try { sessionStorage.removeItem(prefix + key); } catch (e) { return croak(e); }
                        return true;                        
                }
                return false;
        };
        
        /**
         * Remove the specified key/value pair from the in-memory store.
         * 
         * NOTE: The in-memory storage does not use prefixes.
         * 
         * @param {String} key The name of the value to remove.
         * @return {boolean} True on success, else false.
         * @private
         */
        function removeFromMemory(key) {
                delete ram[key];
                return true;
        }
        
        /**
         * Clear all key/value pairs form the local store.
         *
         * NOTE: If a prefix has been specified in the module constant 'prefix' then only 
         * values with that specific prefix will be removed. 
         * 
         * @return {boolean} True on success, else false.
         * @private
         */
        function clearLocal() {
                if (!hasLocalStorage) return false;
                if (!!prefix) {
                        var prefixLength = prefix.length;
                        try {
                                for (var key in localStorage) 
                                        if (key.substr(0, prefixLength) === prefix) 
                                                localStorage.removeItem(key);
                        } catch (e) { return croak(e); }
                        return true;
                }
                try { localStorage.clear(); } catch (e) { return croak(e); }
                return true;
        };
        
        /**
         * Clear all key/value pairs form the session store.
         *
         * NOTE: If a prefix has been specified in the module constant 'prefix' then only 
         * values with that specific prefix will be removed. 
         * 
         * @return {boolean} True on success, else false.
         * @private
         */
        function clearSession() {
                if (!hasSessionStorage) return false;
                if (!!prefix) {
                        var prefixLength = prefix.length;
                        try {
                                for (var key in sessionStorage) 
                                        if (key.substr(0, prefixLength) === prefix) 
                                                sessionStorage.removeItem(key);
                        } catch (e) { return croak(e); }
                        return true;
                }
                try { sessionStorage.clear(); } catch (e) { return croak(e); }
                return true;
        };
        
        /**
         * Clear all key/value pairs form the in-memory store.
         *
         * NOTE: The in-memory storage does not use prefixes. 
         * 
         * @return {boolean} True on success, else false.
         * @private
         */
        function clearMemory() {
                ram = {};
                return true;
        }
        
        /**
         * Test the client's support for storing values in the local store.
         *
         * @return {boolean} True if the client has support for the local store, else false.
         * @private
         */
        function testLocalStorage() {
            try {
                localStorage.setItem(prefix + 'angular.webStorage.test', true);
                localStorage.removeItem(prefix + 'angular.webStorage.test');
                return true;
            } catch (e) {
                    return false;
            }
        }
        
        /**
         * Test the client's support for storing values in the session store.
         *
         * @return {boolean} True if the client has support for the session store, else false.
         * @private
         */
        function testSessionStorage() {
            try {
                sessionStorage.setItem(prefix + 'angular.webStorage.test', true);
                sessionStorage.removeItem(prefix + 'angular.webStorage.test');
                return true;
            } catch (e) {
                    return false;
            }
        }
        
        /**
         * Helper method, broadcasts an error notification on exceptions.
         *
         * @return {boolean} Always returns false.
         * @private
         */
        function croak(error) {
                $rootScope.$broadcast(errorName, error.title + ': ' + error.message);
                return false;
        }
        
        return webStorage;
}]);



// Support for localStorage, compatible with old browsers, like Internet 
// Explorer < 8 (tested and working even in Internet Explorer 6).
// Source From: https://developer.mozilla.org/en-US/docs/DOM/Storage
if (!window.localStorage) {
        window.localStorage = {
                getItem : function(sKey) {
                        if (!sKey || !this.hasOwnProperty(sKey)) {
                                return null;
                        }
                        return unescape(document.cookie.replace(new RegExp("(?:^|.*;\\s*)"
                                        + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&")
                                        + "\\s*\\=\\s*((?:[^;](?!;))*[^;]?).*"), "$1"));
                },
                key : function(nKeyId) {
                        return unescape(document.cookie.replace(/\s*\=(?:.(?!;))*$/, "")
                                        .split(/\s*\=(?:[^;](?!;))*[^;]?;\s*/)[nKeyId]);
                },
                setItem : function(sKey, sValue) {
                        if (!sKey) {
                                return;
                        }
                        document.cookie = escape(sKey) + "=" + escape(sValue)
                                        + "; expires=Tue, 19 Jan 2038 03:14:07 GMT; path=/";
                        this.length = document.cookie.match(/\=/g).length;
                },
                length : 0,
                removeItem : function(sKey) {
                        if (!sKey || !this.hasOwnProperty(sKey)) {
                                return;
                        }
                        document.cookie = escape(sKey)
                                        + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
                        this.length--;
                },
                hasOwnProperty : function(sKey) {
                        return (new RegExp("(?:^|;\\s*)"
                                        + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\="))
                                        .test(document.cookie);
                }
        };
        window.localStorage.length = (document.cookie.match(/\=/g) || window.localStorage).length;
}

// Support for sessionStorage, compatible with old browsers, like Internet
// Explorer < 8 (tested and working even in Internet Explorer 6).
// Source From: https://developer.mozilla.org/en-US/docs/DOM/Storage
if (!window.sessionStorage) {
        window.sessionStorage = {
                getItem : function(sKey) {
                        if (!sKey || !this.hasOwnProperty(sKey)) {
                                return null;
                        }
                        return unescape(document.cookie.replace(new RegExp("(?:^|.*;\\s*)"
                                        + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&")
                                        + "\\s*\\=\\s*((?:[^;](?!;))*[^;]?).*"), "$1"));
                },
                key : function(nKeyId) {
                        return unescape(document.cookie.replace(/\s*\=(?:.(?!;))*$/, "")
                                        .split(/\s*\=(?:[^;](?!;))*[^;]?;\s*/)[nKeyId]);
                },
                setItem : function(sKey, sValue) {
                        if (!sKey) {
                                return;
                        }
                        document.cookie = escape(sKey) + "=" + escape(sValue) + "; path=/";
                        this.length = document.cookie.match(/\=/g).length;
                },
                length : 0,
                removeItem : function(sKey) {
                        if (!sKey || !this.hasOwnProperty(sKey)) {
                                return;
                        }
                        document.cookie = escape(sKey)
                                        + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
                        this.length--;
                },
                hasOwnProperty : function(sKey) {
                        return (new RegExp("(?:^|;\\s*)"
                                        + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\="))
                                        .test(document.cookie);
                }
        };
        window.sessionStorage.length = (document.cookie.match(/\=/g) || window.sessionStorage).length;
}