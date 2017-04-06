<template>
    <div>
        <nav-bar></nav-bar>
        <div class="container">
            <router-view></router-view>
        </div>
    </div>
</template>

<style>
    body {
        padding-top: 65px;
    }
</style>
<script>
    //TEMPORARY ADD AUTHENTICATION HERE (TODO)
    import {mapActions} from 'vuex'

    import NavBar from './assets/general/navBar.vue'
    export default {
        components: {
            NavBar
        },
        methods: {
            ...mapActions([
                'authenticate',
                'fetchAccount'
            ])
        }
    }
</script>
