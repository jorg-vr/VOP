<!--
A button usable for navigating to the previous page. This previous page has to be decided by the programmer.

@param route: The route to return to when clicking this button
-->

<template>
    <button @click="back" class="btn btn-default pull-left" id="back">{{$t('common.back') | capitalize }}</button>
</template>
<script>
    import buttonLink from './buttonLink.vue'
    import {mapMutations} from 'vuex'

    export default {
        components: {
            buttonLink
        },
        props: {
            route: Object,
        },
        methods: {
            back(){
                let redirectRoute = this.$store.getters.popVisitedRoute;
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
