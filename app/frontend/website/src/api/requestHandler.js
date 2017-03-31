import Vue from 'vue'

const headers = {
    Accept: "application/json"
};

export default {

    getObjectsRequest(location){
        return new Promise(resolve => {
            Vue.http.get(location, headers).then(response => {
                resolve(response.body.data)
            })
        })

    },

    getObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.get(location + id, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    /*
    filters: an array of objects, each have a filter and its value.
    This object can look like this: {filter: 'company', value: 'Hertsens'}
     */
    getObjectsRequestBy(location, filters){
        query = ''
        if(filters.length > 0){
            query += '?'
            for(let i=0; i<filters.length; i++){
                let filter = filters[i]
                query += filters[i].filter + '=' + filters[i].value
            }
        }
        return getObjectsRequest(location + query)
    },

    postObjectRequest(location, object){
        return new Promise(resolve => {
            Vue.http.post(location, object, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    putObjectRequest(location, object){
        return new Promise(resolve => {
            Vue.http.put(location + object.id, object, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    deleteObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.delete(location + id, headers).then(() => {
                resolve()
            })
        })
    },
}

