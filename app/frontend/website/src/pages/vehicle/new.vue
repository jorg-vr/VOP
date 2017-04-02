
<template>
    <div>
        <div class="page-header">
            <h1>{{ $t("vehicle.vehicle") | capitalize }} {{$t("actions_plural.create") }}</h1>
        </div>
        <vehicle-form :submit="createVehicle" :fleetId="fleetId"></vehicle-form>
    </div>
</template>
<script>
    import VehicleForm from '../../assets/form/types/vehicleForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            VehicleForm
        },
        props: {
            fleetId: String
        },
        methods: {
            ...mapActions([
                'createVehicle'
            ])
        }
    }
</script>