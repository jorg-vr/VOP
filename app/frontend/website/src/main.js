import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './config/routes'

Vue.use(VueRouter)

// Pointing routes to the components they should use
const router = new VueRouter({
    routes
})

new Vue({
    router
}).$mount('#app')
