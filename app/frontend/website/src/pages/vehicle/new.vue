
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("common.new") | capitalize}} {{$t("vehicle.vehicle")}}</h1>
        </div>
        <vehicle-form :submit="createVehicle" :vehicle="vehicle"></vehicle-form>
    </div>
</template>
<script>
    import VehicleForm from '../../assets/form/types/vehicleForm.vue'
    import {mapActions} from 'vuex'

    export default {
        data(){
            return {
                vehicle: {}
            }
        },
        components: {
            VehicleForm
        },
        created() {
            if(this.$route.params.fleet_id){
                this.vehicle.fleet = this.$route.params.fleet_id;
            }
        },
        methods: {
            ...mapActions([
                'createVehicle'
            ])
        }
    }
</script>