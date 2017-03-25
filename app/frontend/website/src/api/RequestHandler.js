import Vue from 'vue'

const base_url = 'https://vopro5.ugent.be/app/api/';
const headers = {
    Accept: "application/json"
};

export default {

    getObjectsRequest(location){
        return new Promise(resolve => {
            Vue.http.get(base_url + location, headers).then(response => {
                resolve(response.body.data)
            })
        })

    },

    getObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.get(base_url + location + id, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    postObjectRequest(location, object){
        return new Promise(resolve => {
            Vue.http.post(base_url + location, object, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    putObjectRequest(location, object){
        return new Promise(resolve => {
            Vue.http.post(base_url + location + object.id, object, headers).then(response => {
                resolve(response.body)
            })
        })
    },

    deleteObjectRequest(location, id){
        return new Promise(resolve => {
            Vue.http.delete(base_url + location + id, headers).then(() => {
                resolve()
            })
        })
    },
}

