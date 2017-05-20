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

router.base = Vue.config.env.BASE
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

new Vue({
    store,
    router
}).$mount('#app')

