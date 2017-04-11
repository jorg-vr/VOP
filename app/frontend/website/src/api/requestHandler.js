import Vue from 'vue'


export default {

    getObjectsRequest(location){
        return new Promise(resolve => {
            Vue.http.get(location).then(response => {
                resolve(response.body.data)
            })
        })
    },

    getObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.get(location + id).then(response => {
                resolve(response.body)
            })
        })
    },

    //This function isn't optimised yet for any use case!
    //For example, some properties can't be filtered, some properties might be nested
    getObjectsRequestBy(location, filters){
        let query = '?'
        for(const filter in filters){
            if(filters.hasOwnProperty(filter)){
                query += filter + '=' + filters[filter] + '&'
            }
        }
        query =  query.slice(0, -1)
        return this.getObjectsRequest(location + query)
    },

    //This version returns the body instead of the full response
    postObjectRequestGetBody(location, object){
        return new Promise(resolve => {
            this.postObjectRequest(location, object).then(response => {
                resolve(response.body)
            })
        })
    },

    postObjectRequest(location, object){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.post(location, object).then(response => {
                resolveSuccess(response)
            }, response => {
                resolveFailure(response)
            })
        })
    },


    putObjectRequest(location, object){
        return new Promise(resolve => {
            Vue.http.put(location + object.id, object).then(response => {
                resolve(response.body)
            })
        })
    },

    deleteObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.delete(location + id).then(() => {
                resolve()
            })
        })
    },
}

