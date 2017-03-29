let nonAcceptedFilters = ['id', 'createdAt', 'lastUpdated', 'url']

Array.prototype.unique = function() {
    var a = this.concat();
    for(var i=0; i<a.length; ++i) {
        for(var j=i+1; j<a.length; ++j) {
            if(a[i] === a[j])
                a.splice(j--, 1);
        }
    }

    return a;
};

export default {
    filterByAll: () => (objects, value) => {
        let filteredObjects = []
        if(objects.length>0){
            let object = objects[0]
            for(const property in object){
                if(object.hasOwnProperty(property) && nonAcceptedFilters.indexOf(property)===-1){
                    filteredObjects = filteredObjects.concat(objects.filter(obj => {
                        if(obj[property]!==null && String(obj[property]).toLowerCase().includes(value.toLowerCase()))
                            return obj
                    })).unique()
                }
            }
        }
        return filteredObjects
    }
}