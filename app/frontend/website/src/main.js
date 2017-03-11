import Vue from 'vue'
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
//import VueI18n from 'vue-i18n'
import routes from './config/routes'
import locales from './lang/locales'

//Routing support
Vue.use(VueRouter);
//Backend support
Vue.use(VueResource);
//Language support
//Vue.use(VueI18n);
/*
Object.keys(locales).forEach(function (lang) {
    Vue.locale(lang, locales[lang])
});
*/
const router = new VueRouter({
    mode: 'history',
    routes: routes
});

new Vue({
    router
}).$mount('#app');