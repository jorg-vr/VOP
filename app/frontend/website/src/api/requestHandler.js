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

