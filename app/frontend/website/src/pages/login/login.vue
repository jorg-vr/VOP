<template>
    <login-modal @confirmLogin="switchLayout()" v-show="showLogin"></login-modal>
</template>
<script>
    import { mapGetters, mapMutations } from 'vuex'
    import LoginModal from '../../assets/general/loginModal.vue'

    export default {
        components: {
            LoginModal
        },
        data() {
            return {
                showNavBar : false,
            }
        },
        computed: {
            ...mapGetters([
                'nextRoute',
                'showLogin'
            ]),
            //TEMPORARY
        },
        methods: {
            ...mapMutations({
                setActiveAccount: 'SET_ACTIVE_ACCOUNT'
            }),
            switchLayout() {
                this.setActiveAccount({account : {id: '123', name: 'test'}}) //TEMPORARY
                if(this.nextRoute.path !== null){
                    this.$router.push({name: this.nextRoute.name, params: this.nextRoute.params})
                }
                else {
                    console.log('home')
                    this.$router.push({name: 'home'})
                }
            }
        },
    }
</script>