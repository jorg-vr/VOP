import Vue from 'vue'

import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'

import routes from './config/routes'
import locales from './lang/locales'
import store from './store'

import environments from './config/environments'

//Routing support
Vue.use(VueRouter);
//Backend support
Vue.use(VueResource);
//Language support
Vue.use(VueI18n);

Vue.config.lang = 'nl';

if(process.env.NODE_ENV){
    Vue.config.env = environments[process.env.NODE_ENV]
}
else {
    Vue.config.env = environments['development']
}

Object.keys(locales).forEach(function (lang) {
    Vue.locale(lang, locales[lang])
})

const router = new VueRouter({
    mode: 'history',
    base: Vue.config.env.BASE,
    routes: routes,
})

Vue.filter('capitalize', function(value){
    value = value.toString()
    return value.charAt(0).toUpperCase() + value.slice(1)
})

Vue.http.options.root = Vue.config.env.API_KEY

new Vue({
    store,
    router
}).$mount('#app')

