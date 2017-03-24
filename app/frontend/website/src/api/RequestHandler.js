import Vue from 'vue'

const base_url = 'https://vopro5.ugent.be/app/api/';
const headers = {
    Accept: "application/json"
};

export default {
    getRequest(url){
        return Vue.http.get(base_url + url, headers)
    },

    postRequest(url, object){
        return Vue.http.post(base_url + url, object, headers)
    },

    putRequest(url, object){
        return Vue.http.put(base_url + url, object, headers)
    },

    deleteRequest(url, object){
        return Vue.http.delete(base_url + url, headers)
    }
}

