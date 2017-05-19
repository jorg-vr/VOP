
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

/**
 * Add a showable date property to the list of objects with a formatted date.
 * @param objects A list of objects
 * @param propertyName The date property of the objects
 */
export const addShowableDate = function(objects, propertyName){
    if(objects.length > 0 && objects[0][propertyName]){
        for(let i=0; i<objects.length; i++){
            objects[i]['showable' + capitalize(propertyName)] =  objects[i][propertyName].showableDate()
        }
    }
}

/**
 * Add a showable dateTime property to the list of objects with a formatted date.
 * @param objects A list of objects
 * @param propertyName The date property of the objects
 */
export const addShowableDateTime = function(objects, propertyName){
    if(objects.length > 0 && objects[0][propertyName]){
        for(let i=0; i<objects.length; i++){
            objects[i]['showable' + capitalize(propertyName)] =  objects[i][propertyName].showableDateTime()
        }
    }
}

/**
 * Capitalize a string
 * @param value
 * @returns {string}
 */
export const capitalize = function(value) {
    return value.charAt(0).toUpperCase() + value.slice(1);
}

export const translateSuretyTypes = function (array) {
    for(let i=0;i<array.length;i++){
        array[i].suretyTypeTranslation=Vue.t('suretyTypes.'+array[i].suretyType).capitalize();
    }
    return array
}

export const centsToEuroArray = function (array,field) {
    for(let i=0;i<array.length;i++){
        array[i]=centsToEuroObject(array[i],field);
    }
    return array
}

export const centsToEuroObject = function (object,field) {
    object[field+"Euro"] = "€" + object[field]/100;
    return object
}
