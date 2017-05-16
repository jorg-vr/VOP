<!--
A button usable for navigating to the previous page. This previous page has to be decided by the programmer.

@param route: The route to return to when clicking this button
-->

<template>
    <button type="button" @click="back" :class="buttonClass" id="back">{{ text | capitalize }}</button>
</template>
<script>
    import {mapMutations, mapActions} from 'vuex'

    export default {
        props: {
            route: Object,
            text: {
                type: String,
                default() {
                    return this.$t('common.back')
                }
            },
            buttonClass: {
                type: String,
                default: "btn btn-default pull-left"
            }
        },
        methods: {
            back(){
                this.$store.commit('setIsGoingBack', {status: true})
                let redirectRoute = this.$store.getters.popVisitedRoute;
                while(!(redirectRoute===undefined || redirectRoute.name===null) && redirectRoute.path === this.$route.path){
                    redirectRoute = this.$store.dispatch('popVisitedRoute');
                }
                if(redirectRoute===undefined || redirectRoute.name===null){
                    this.$router.push({name: this.route.name, params: this.route.params})
                }
                else {
                    this.$router.push(redirectRoute.path)
                }
            }
        }
    }
</script>
<style>
    #back {
        margin-top: 50px;
    }
    form #back {
        margin-top: 25px;
    }
</style>