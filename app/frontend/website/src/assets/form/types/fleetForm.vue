<!--
    This page is used to generate a form for a user.
-->
<template>
    <form-component v-if="fleet" :actions="actions" :resource="resource" :object="fleet">
        <fleet-form-input :fleet="fleet"></fleet-form-input>
    </form-component>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import fleetFormInput from './fleetFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET,
            }
        },
        components: {
            formComponent, fleetFormInput
        },
        created(){
            if(this.isAuthorizedForOwnResourcesButNotAll(this.resource, this.actions)){

            }
        },
        props: {
            actions: Object,
            oldFleet: Object,
            clientId: String
        },
        computed: {
            ...mapGetters([
                'isAuthorizedForOwnResourcesButNotAll',
                'activeFunction'
            ]),
            fleet(){
                if(this.oldFleet === null) {
                    return {}
                }
                else {
                    return this.oldFleet
                }
            }
        },
        methods: {
            proceed(){
                this.$store.dispatch(action + 'Fleet').then(() => {
                    this.submit(this.fleet).then(() => {
                        this.$router.push({name: 'fleets'})
                    })
                })

            }
        }
    }
</script>
