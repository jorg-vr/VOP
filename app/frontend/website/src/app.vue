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
    import {mapGetters} from 'vuex'

    export default {
        components: {
            NavBar
        },
        computed: {
            ...mapGetters([
                'finishedLoading'
            ])
        }
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
</style>
