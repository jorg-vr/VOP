<!--
    This page is used to edit a certain vehicle.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <vehicle-form :actions="actions" :oldVehicle="vehicle"></vehicle-form>
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

