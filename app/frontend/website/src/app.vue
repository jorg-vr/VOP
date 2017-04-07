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
    import NavBar from './assets/general/navBar.vue'

    export default {
        components: {
            NavBar
        }
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
</style>