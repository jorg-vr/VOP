<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("log.log") | capitalize}} {{$t("vehicle.vehicle")}} {{vehicle.licensePlate}}
            </h1>
            <logs :id="id" resource="vehicles"></logs>
        </div>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import logs from '../log/index.vue'
    export default {
        data(){
            return {
                vehicle: {},
            }
        },
        props: {
            id: String
        },
        components: {
            logs
        },
        created(){
            this.fetchVehicle({id: this.id}).then(vehicle => {
                this.vehicle = vehicle
            })
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
            ])
        }
    }
</script>