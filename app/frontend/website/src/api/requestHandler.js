import Vue from 'vue'


export default {

    getObjectsRequest(location){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.get(location).then(response => {
                resolveSuccess(response.body.data)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    getObjectRequest(location, id){
        return new Promise((resolveSucces, resolveFailure) => {
            Vue.http.get(location + id).then(response => {
                resolveSucces(response.body)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    //This function isn't optimised yet for any use case!
    //For example, some properties can't be filtered, some properties might be nested
    getObjectsRequestBy(location, filters){
        let locationTrimmed = location.rtrim('/')
        let query = '?'
        for(const filter in filters){
            if(filters.hasOwnProperty(filter)){
                query += filter + '=' + filters[filter] + '&'
            }
        }
        query =  query.slice(0, -1)
        return this.getObjectsRequest(locationTrimmed + query)
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
        console.log(object)
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.put(location + object.id, object).then(response => {
                resolveSuccess(response.body)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    deleteObjectRequest(location, id){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.delete(location + id).then(() => {
                resolveSuccess()
            }, response => {
                resolveFailure(response)
            })
        })
    },
}

