<!--
This page is used to edit or create a certain fleet.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <fleet-form :actions="actions" :oldFleet="oldFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../../assets/form/types/fleetForm.vue'
    import actions from '../../../constants/actions'
    import {mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('fleet', this.actions.name),
                oldFleet: null
            }
        },
        components: {
            FleetForm
        },
        created(){
            if(this.id){
                this.fetchFleet({id: this.id}).then(fleet => {
                    this.oldFleet = fleet
                })
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        methods: {
            ...mapActions([
                'fetchFleet'
            ])
        }
    }
</script>

