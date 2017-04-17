<!--
This page is used to show a form for a client.

@param actions: the action this form is intended for (create/update)
@param oldFleet (optional): The old client if the form is intended to a update fleet
@param clientId (optional): The ID of the client who owns this fleet.
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
            //If the user can only create/update fleets for his own company then the company can already be configured.
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
