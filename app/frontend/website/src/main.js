import Vue from 'vue'

import VeeValidate, {Validator} from 'vee-validate'
import VueResource from 'vue-resource'
import VueI18n from 'vue-i18n'

import router from './config/routes'
import locales from './lang/locales'
import store from './store'
import environments from './config/environments'

import nl from 'vee-validate/dist/locale/nl';
import check_vin from './validators/check_vin'
import length from './validators/length'


Validator.extend('length', length)
Validator.extend('check_vin', check_vin)
Validator.addLocale(nl)

//Validation support
Vue.use(VeeValidate);
//Backend support
Vue.use(VueResource);
//Language support
Vue.use(VueI18n);

if(process.env.NODE_ENV){
    Vue.config.env = environments[process.env.NODE_ENV]
}
else {
    Vue.config.env = environments['development']
}

Vue.http.options.root = Vue.config.env.API_KEY
Vue.http.headers.common['Accept'] = 'application/json'

Object.keys(locales).forEach(function (lang) {
    Vue.locale(lang, locales[lang])
})

window.document.base = Vue.config.env.BASE
let locale = localStorage.getItem('languageCode')
store.commit('setLanguage', {language: locale ? locale : 'nl'})

//A list of general functions

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

String.prototype.rtrim = function(s) {
    return this.replace(new RegExp(s + "*$"),'');
};

String.prototype.showableDate = function() {
    let d = new Date(this)
    return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
}

String.prototype.showableDateTime = function() {
    let d = new Date(this)
    let hours = d.getHours().toString().length==2 ? d.getHours() : '0' + d.getHours()
    let minutes = d.getMinutes().toString().length==2 ? d.getMinutes() : '0' + d.getMinutes()
    return this.showableDate() + ' ' + hours + ':' + minutes
}

String.prototype.plural = function() {
    return this + 's'
}

Vue.filter('capitalize', function(value){
    return value.capitalize()
})

router.beforeEach((to, from, next) => {
    console.log(to)
    //This variable must be false at the start of a page!
    if(to.name === 'login'){
        next()
    }
    else {
        let token = localStorage.getItem('authToken')
        if(token){
            store.commit('setAuthToken', {authToken: token})
            store.dispatch('refreshToken').then(() => {
                if(store.getters.hasPermissionForRoute(to.name)){
                    store.commit('setError', null) //Reset the state errors. No errors have been thrown yet on the new page.
                    next()
                }
                else {
                    if(from.name !== null){ //If the user comes from a page. Let the user remain on that page.
                        next(false)
                    }
                    else { //If the user does not come from a page. Redirect the user to the home page
                        next({name: 'homeClient'})
                    }
                }
            }, () => {
                store.commit('setNextRoute' , {route: to})
                next({name: 'login'});
            })
        }
        else {
            store.commit('setNextRoute' , {route: to})
            next({name: 'login'});
        }
    }
    if(!store.getters.isGoingBack){
        store.commit('pushVisitedRoute', {route: from})
    }
    else {
        store.commit('setIsGoingBack', {status: false})
    }
})

new Vue({
    store,
    router
}).$mount('#app')

