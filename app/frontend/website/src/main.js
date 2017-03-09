import Vue from 'vue'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import routes from './config/routes'

Vue.use(VueRouter)
Vue.use(VueI18n)

Vue.config.lang = 'nl'

const router = new VueRouter({
    mode: 'history',
    routes: routes
})

new Vue({
    router
}).$mount('#app');