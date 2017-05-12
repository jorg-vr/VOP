<!--
    This page shows a certain vehicle in detail.

    @param id: The id of the vehicle to be shown.
-->
<template>
    <div v-if="vehicle">
        <div class="page-header">
            <h1> {{$t('vehicle.vehicle') | capitalize }} {{vehicle.licensePlate}}</h1>
        </div>
        <div class="col-md-8">
            <table id="show-vehicle" class="table show-table">
                <tr>
                    <td>{{$t('vehicle.licensePlate') | capitalize }}</td>
                    <td>{{vehicle.licensePlate}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.vin') | capitalize }}</td>
                    <td>{{vehicle.vin}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.brand') | capitalize }}</td>
                    <td>{{vehicle.brand}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.model') | capitalize }}</td>
                    <td>{{vehicle.model}}</td>
                </tr>
                <tr v-if="vehicleType">
                    <td>{{$t('vehicle.vehicleType') | capitalize }}</td>
                    <td>{{vehicleType.name}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.mileage') | capitalize }}</td>
                    <td>{{vehicle.mileage}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.year') | capitalize }}</td>
                    <td>{{vehicle.year}}</td>
                </tr>
            </table>
            <button-link buttonId="log" :route="{name: 'vehicle_logs'}">{{$t('log.log') | capitalize}}</button-link>
            <button-back v-if="vehicle.fleet" :route="{name: 'fleet', params: {id: vehicle.fleet}}"></button-back>
            <button-back v-else :route="{name: 'fleets'}"></button-back>
        </div>

    </div>
</template>
<script>
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            buttonBack, buttonLink
        },
        props: {
            id: String
        },
        created() {
            this.fetchVehicle({id: this.id}).then(vehicle => {
                this.fetchVehicleType({id: vehicle.type})
            })
        },
        computed: {
            ...mapGetters([
                'vehicle',
                'vehicleType',
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
                'fetchVehicleType',
            ])
        }
    }
</script>
<style>
    #log {
        margin-right: 10px;
    }
</style>
