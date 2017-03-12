<template>
    <div id="page-content-wrapper">
        <nav-bar></nav-bar>
        <div class="container-fluid">
            <div class="row">
                <router-view></router-view>
            </div>
            <button id="back-button" class="btn btn-primary table-button" v-on:click="goBack">Ga terug</button>

        </div>
    </div>
</template>
<script>
    import NavBar from './NavBar.vue'
    export default {
        components: {
            NavBar
        },
        methods: {
            goBack() {
                this.$router.go(-1);
            }
        }
    }
</script>
