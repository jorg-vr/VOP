<template>
    <div v-if="finishedLoading">
        <nav-bar></nav-bar>
        <div class="container">
            <router-view></router-view>
        </div>
    </div>
</template>

<style>
    body {
        padding-top: 65px;
        margin-bottom: 50px;
    }
</style>
<script>
    import NavBar from './assets/general/navBar.vue'
    import {mapGetters, mapMutations} from 'vuex'

    export default {
        components: {
            NavBar
        },
        computed: {
            ...mapGetters([
                'hasActiveAccount', 'finishedLoading'
            ])
        },
        methods: {
            ...mapMutations([
                'setNextRoute'
            ])
        },
        beforeRouteEnter: ((to, from, next) => {
            if(to.path !== '/login' && !this.hasActiveAccount){
                console.log('1')
                next(vm => {
                    console.log('2')
                    vm.$store.commit('setNextRoute' , {route: to})
                })
                console.log('3')
                next({path: '/login'})
            }
            else {
                next()
            }
        })
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
</style>
