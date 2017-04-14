<!--
    This page is used to generate a form for a user.
-->
<template>
    <form-component v-if="fleet" :actions="actions" :resource="resource" :object="fleet">
        <fleet-form-input :fleet="fleet"></fleet-form-input>
    </form-component>
</template>
<script>
    import {mapGetters} from 'vuex'
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
                this.fleet.company = this.activeFunction.company
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
                    return {company: this.clientId}
                }
                else {
                    return this.oldFleet
                }
            }
        }
    }
</script>
