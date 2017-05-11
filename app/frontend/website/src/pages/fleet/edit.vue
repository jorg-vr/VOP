<!--
    This page is used to edit a certain fleet.

    @param id: the id of the old fleet.
-->
<template>
    <abstract-form :actions="actions" :object="fleet" :back="back" :resource="resource">
        <form-input :object="fleet"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './fleetFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET,
                actions: actions.UPDATE,
                fleet: {},
                back:{name:resources.FLEET.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchFleet({id: this.id}).then(o => {
                    this.fleet = o;
                })
            }
            //If the user can only create/update fleets for his own company then the company can already be configured.
            if(this.isAuthorizedForOwnResourcesButNotAll(this.resource, this.actions)){
                this.fleet.company = this.activeFunction.company
            }
        },
        components: {
            abstractForm,formInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchFleet'
            ])

        }
    }
</script>

