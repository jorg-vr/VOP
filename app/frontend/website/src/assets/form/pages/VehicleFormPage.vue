<!--
This page is used to edit or create a certain fleet.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
@param fleetId (optional): The fleetId of new/updated vehicle
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <vehicle-form :actions="actions" :oldVehicle="vehicle" :fleetId="fleetId"></vehicle-form>
    </div>
</template>
<script>
    import VehicleForm from '../../../assets/form/types/vehicleForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('vehicle', this.actions.name)
            }
        },
        components: {
            VehicleForm
        },
        created(){
            if(this.id){
                this.fetchVehicle({id: this.id})
            }
        },
        props: {
            id: String,
            fleetId: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'vehicle'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
            ])
        }
    }
</script>

