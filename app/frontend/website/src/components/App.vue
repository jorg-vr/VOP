<template>
    <div id="content-wrapper">
        <nav-bar></nav-bar>
        <div class="container-fluid">
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
    import NavBar from './NavBar.vue'
    export default {
        components: {
            NavBar
        }
    }
</script>