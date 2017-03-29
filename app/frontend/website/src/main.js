import Vue from 'vue'

import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'

import routes from './config/routes'
import locales from './lang/locales'
import store from './store'

import rootUrl from './config/rootUrl'

//Routing support
Vue.use(VueRouter);
//Backend support
Vue.use(VueResource);
//Language support
Vue.use(VueI18n);

Vue.config.lang = 'nl';

Object.keys(locales).forEach(function (lang) {
    Vue.locale(lang, locales[lang])
})

const router = new VueRouter({
    mode: 'hash',
    base: window.location.href,
    routes: routes,
})

Vue.filter('capitalize', function(value){
    value = value.toString()
    return value.charAt(0).toUpperCase() + value.slice(1)
})

new Vue({
    store,
    router
}).$mount('#app')

