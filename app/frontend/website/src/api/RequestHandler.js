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

    },

    postObjectRequest(location, object){
        return Vue.http.post(base_url + location, object, headers)
    },

    putObjectRequest(location, object){
        return Vue.http.put(base_url + location, object, headers)
    },

    deleteObjectRequest(location, id){
        return Vue.http.delete(base_url + location, headers)
    },
}

