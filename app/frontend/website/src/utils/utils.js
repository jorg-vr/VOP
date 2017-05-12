
import Vue from 'vue'

/**
 * Returns the title for a page with the given resourceName and actionName
 * @param resourceName
 * @param actionsName
 */
export const getResourceActionText = function (resourceName, actionsName) {
    return Vue.t('actions.' + actionsName, {subject: Vue.t(resourceName + '.' + resourceName)}).capitalize()
}

//This could probably be used aswell: http://es6-features.org/#CustomInterpolation
/**
 * Formats a location string with it's parameters
 * E.g. str = '/vehicles/{id}/logs', data = {id: 5}
 * => return value = '/vehicles/5/logs'
 * @param str The location string
 * @param data The parameters of the string
 * @returns {*}
 */
export const formatLocation = function(str, data) {
    data = data || {}
    var match = str.match(/{(.+?)}/g)
    if(match){
        match.forEach(function(key) {
            str = str.replace(key, data[key.replace('{','').replace('}', '')])
        });
    }
    return str
}