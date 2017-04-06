<template>
    <div id="page-content-wrapper">
        <nav-bar v-show="showNavBar"></nav-bar>
        <!-- login screen as modal -->
        <login-modal @confirmLogin="switchLayout()" v-show="showLogin"></login-modal>
        <div class="container-fluid">
            <div class="row">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>
    import { mapGetters } from 'vuex'
    import NavBar from './assets/general/navBar.vue'
    import LoginModal from './assets/general/loginModal.vue'
    export default {
        components: {
            NavBar,
            LoginModal
        },
        data() {
            return {
                showNavBar : false, 
            }
        },
        methods: {
            switchLayout:function(id){
                this.showNavBar=true
            }
        },
         computed: {
            ...mapGetters([
                'showLogin'
            ])
        },
    }
</script>
<style>
    body {
        padding-top: 65px;
    }
</style>