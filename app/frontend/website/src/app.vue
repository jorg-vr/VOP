<template>
    <div id="page-content-wrapper">
        <nav-bar></nav-bar>
        <div class="container-fluid">
            <div class="row">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>

    import NavBar from './assets/general/navBar.vue'
    export default {
        components: {
            NavBar
        }
    }
</script>
