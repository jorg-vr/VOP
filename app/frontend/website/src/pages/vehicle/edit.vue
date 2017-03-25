<!--
    This page is used to edit a vehicle.
    It shows the vehifleetForm.vue.vue with the update vehicle method.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{ $t("vehicle.vehicle") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <vehicle-form :submit="updateVehicle" :oldVehicle="vehicle"></vehicle-form>
    </div>
</template>
<script>
    import VehicleForm from '../../assets/form/types/vehicleForm.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            VehicleForm
        },
        //Fetch the vehicle of this page when the page is created.
        created() {
            this.fetchVehicle({id: this.$route.params.id})
        },
        computed: {
            ...mapGetters([
                'vehicle'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
                'updateVehicle'
            ])
        }
    }
</script>