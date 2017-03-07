import Vue from 'vue'
import App from './App.vue'
import Home from './components/Home.vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// Pointing routes to the components they should use
const router = new VueRouter({
    routes: [
        { path: '/home', component: Home },
        { path: '*', redirect: '/home'}
    ]
})

new Vue({
    router
}).$mount('#app')
