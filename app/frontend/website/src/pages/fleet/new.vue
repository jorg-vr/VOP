<!--
    This page is used to create a new fleet.
-->
<template>
    <abstract-form :actions="actions" :object="fleet" :back="back" :resource="resource">
        <fleet-form-input :object="fleet"></fleet-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import fleetFormInput from './fleetFormInput.vue'
    import {mapGetters} from 'vuex'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.FLEET,
                fleet: {},
                back:{name:resources.FLEET.name.plural()}
            }
        },
        created(){
            //If the user can only create/update fleets for his own company then the company can already be configured.
            if(this.isAuthorizedForOwnResourcesButNotAll(this.resource, this.actions)){
                this.fleet.company = this.activeFunction.company
            }
        },
        components: {
            abstractForm,fleetFormInput
        },
        computed: {
            ...mapGetters([
                'isAuthorizedForOwnResourcesButNotAll'
            ])
        }
    }
</script>