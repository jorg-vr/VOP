<template>
    <div id="wrapper">
        <nav-bar></nav-bar>
        <div id="page-wrapper">
            <router-view></router-view>
        </div>
    </div>
</template>

<style>
#wrapper {
    background-color: #2f4050;
    min-height: 100vh;
}

#page-wrapper {
    margin-left: 220px;
    background-color: #f3f3f4;
    padding: 0 15px 0 15px;
    min-height: 100vh;
}

.content {
    padding: 20px 20px 20px 20px;
    background-color: white;
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
