let nonAcceptedFilters = ['id', 'createdAt', 'lastUpdated', 'url', 'originalObject']

export default {

    /**
     * Returns a subset of the given objects. Only the objects which have a property which is a substring of
     * the given value are returned.
     * @param objects: the list of objects to be filtered
     * @param value: the value used for filtered
     */
    filterByAll: () => (objects, value) => {
        let filteredObjects = []
        if(objects.length>0){
            let object = objects[0]
            for(const property in object){
                filteredObjects = filteredObjects.concat(
                    concatObjectsWithPartialValueForProperty(objects, filteredObjects, object, property, value)
                ).unique()
            }
        }
        return filteredObjects
    },

    /**
     * Returns a subset of the given objects. The objects of which its properties are substrings of the properties given
     * by the object are returned.
     * @param objects: the list of objects to be filtered
     * @param value: the object used for filtered
     */
    filterByAllAdvanced: () => (objects, object) => {
        let filteredObjects = []
        if(objects.length>0) {
            filteredObjects = objects.filter(object2 => object2.hasPartialValues(object))
        }
        return filteredObjects
    }
}

/**
 * Remove doubles from an array
 * @returns {Array.<*>}
 */
Array.prototype.unique = function() {
    var a = this.concat();
    for(var i=0; i<a.length; ++i) {
        for(var j=i+1; j<a.length; ++j) {
            if(a[i] === a[j])
                a.splice(j--, 1);
        }
    }
    return a;
}


/**
 * Returns if the values of this object equal (a part of) the values of the given object.
 * This is called recursively for nested objects.
 */
Object.defineProperty(Object.prototype, 'hasPartialValues', {
    value: function(object){
        for(const property in object) {
            if (object.hasOwnProperty(property)) {
                if (object[property] !== null) {
                    if (this[property] === null) {
                        return false
                    }
                    else {
                        if (this[property] instanceof Object) {
                            if (!this[property].hasPartialValues(object[property])) {
                                return false
                            }
                        }
                        else {
                            if (!String(this[property]).toLowerCase().includes(object[property].toLowerCase())) {
                                return false
                            }
                        }

                    }
                }
            }
        }
        return true
    },
    enumerable: false
})



/**
 * Concatenates a filtered list of objects to the filteredObjects list.
 * The objects of which its property is a substring of the value given
 * by object[property] are added to the filteredObjects list.
 * @param objects: An array of objects
 * @param filteredObjects: An array to which objects have to be concatenated
 * @param object: The object which has the property
 * @param property: The property which has to be checked
 * @param value: The partial value for the property
 * @returns {*}
 */
let concatObjectsWithPartialValueForProperty = function(objects, filteredObjects, object, property, value){
    if(object.hasOwnProperty(property) && nonAcceptedFilters.indexOf(property)===-1){
        if(object[property] instanceof Object){
            for(const nestedProperty in object[property]){
                //Create array of objects with nestedProperty
                let nestedObjectsArray = []
                for(let i=0; i<objects.length; i++){
                    //The object needs to remember its original object to add to the list later.
                    objects[i][property]['originalObject'] = objects[i]
                    nestedObjectsArray.push(objects[i][property])
                }
                filteredObjects = concatObjectsWithPartialValueForProperty(nestedObjectsArray, filteredObjects, object[property], nestedProperty, value)
            }
        }
        else {
            for(let i=0; i<objects.length; i++) {
                let obj = objects[i]
                if (obj[property] !== null && String(obj[property]).toLowerCase().includes(value.toLowerCase())) {
                    if (obj.hasOwnProperty('originalObject')) {
                        filteredObjects.push(obj['originalObject'])
                        delete obj['originalObject']
                    }
                    else {

                        filteredObjects.push(obj)
                    }
                }
            }
        }
    }
    return filteredObjects
}

