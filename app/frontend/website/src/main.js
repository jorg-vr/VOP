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


console.log(store)

if(process.env.NODE_ENV){
    Vue.config.env = environments[process.env.NODE_ENV]
}
else {
    Vue.config.env = environments['development']
}

Vue.config.lang = 'nl';
Vue.http.options.root = Vue.config.env.API_KEY
Vue.http.headers.common['Accept'] = 'application/json'

Object.keys(locales).forEach(function (lang) {
    Vue.locale(lang, locales[lang])
})

const router = new VueRouter({
    mode: 'history',
    base: Vue.config.env.BASE,
    routes: routes,
})

router.beforeEach((to, from, next) => {
    if(to.path === '/login'){
        next()
    }
    else {
        let token = localStorage.getItem('authToken')
        if(token){
            store.commit('SET_AUTH_TOKEN', {authToken: token})
            store.dispatch('refreshToken').then(() => {
                next()
            }, () => {
                next({path: '/login'});
            })
        }
        else {
            store.commit('setNextRoute' , {route: to})
            next({path: '/login'});
        }
    }

})

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

Vue.filter('capitalize', function(value){
    return value.capitalize()
})


new Vue({
    store,
    router
}).$mount('#app')

