import Vue from 'vue'

/**
 * Interface for communicating with the backend API.
 *
 * All of the functions return a new promise which can be resolved with a success and failure function.
 * For more info on promises please read: https://developers.google.com/web/fundamentals/getting-started/primers/promises
 *
 */

export default {

    /**
     * This function does a GET request to the specified location.
     * @param location: The location of the objects
     * @returns {Promise}
     */
    getObjectsRequest(location){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.get(location).then(response => {
                resolveSuccess(response.body.data)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    /**
     * This function does a GET request to the specified location. The specified ID is added to the location.
     * The goal of this function is to GET request 1 object of a specified resource.
     * @param location: The location of the object
     * @param id: The id of the object
     * @returns {Promise}
     */
    getObjectRequest(location, id){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.get(location + id).then(response => {
                console.log(response)
                resolveSuccess(response.body)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    /**
     * This function does a GET request to the specified location. A query is computed with the given 'filters' object.
     * For example a "vehicle" object with 2 properties: brand and model can be passed. The GET request will make an API
     * call to /vehicles?brand=...&model=...
     * @param location: The location of the objects
     * @param filters
     * @returns {*|Promise}
     */
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

    /**
     * This function does a POST request to the specified location.
     * @param location
     * @param object: The object which should be created.
     * @returns {Promise}
     */
    postObjectRequest(location, object){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.post(location, object).then(response => {
                resolveSuccess(response)
            }, response => {
                resolveFailure(response)
            })
        })
    },


    /**
     * This function does a PUT request to the specified location with the ID of the given object.
     * @param location: The location of the resource of the old object.
     *        Note: NOT the location of the old object!
     * @param object: The object which should be updated with this new value.
     * @returns {Promise}
     */
    putObjectRequest(location, object){
        return new Promise((resolveSuccess, resolveFailure) => {
            Vue.http.put(location + object.id, object).then(response => {
                resolveSuccess(response.body)
            }, response => {
                resolveFailure(response)
            })
        })
    },

    /**
     * This function does a GET request to the specified location. The specified ID is added to the location.
     * @param location
     * @param id: The id of the object to be removed.
     * @returns {Promise}
     */
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

