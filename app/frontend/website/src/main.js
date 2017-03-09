import Vue from 'vue'
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import routes from './config/routes'

//Routing support
Vue.use(VueRouter)
//Backend support
Vue.use(VueResource)
//Language support
Vue.use(VueI18n)

Vue.config.lang = 'nl'

const router = new VueRouter({
    mode: 'history',
    routes: routes
})

new Vue({
    router
}).$mount('#app');