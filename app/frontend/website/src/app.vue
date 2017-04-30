<!--
    Component in which most of the components will be rendered.
-->
<template>
    <div>
        <nav-bar></nav-bar>
        <div class="container">
            <div class="row">
                <router-view></router-view>
            </div>
            <loading-indicator></loading-indicator>
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
    import navBar from './assets/navbar/navBar.vue'
    import loadingIndicator from './assets/general/loadingIndicator.vue'
    import {mapGetters} from 'vuex'
    export default {
        components: {
            navBar, loadingIndicator
        }
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
</style>
