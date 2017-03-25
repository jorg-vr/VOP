<template>
    <div>
        <div class="page-header">
            <h1> {{$t('vehicle.vehicle') | capitalize }} {{vehicle.licensePlate}}</h1>
        </div>
        <div class="col-md-8">
            <table id="show-vehicle" class="table">
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
                <tr>
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
                <tr>
                    <td>{{$t('vehicle.leasingCompany') | capitalize }}</td>
                    <td>{{vehicle.leasingCompany}}</td>
                </tr>
            </table>
            <button-back v-if="vehicle.fleet" :route="{name: 'fleet', params: {id: vehicle.fleet}}"></button-back>
            <button-back v-else :route="{name: 'fleets'}"></button-back>
        </div>

    </div>
</template>
<script>
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            buttonBack
        },
        created() {
            this.fetchVehicle({id: this.$route.params.id}).then(vehicle => {
                this.fetchVehicleType({id: vehicle.type})
                this.fetchClient({id: vehicle.leasingCompany})
            })
        },
        computed: {
            ...mapGetters([
                'vehicle',
                'vehicleType',
                'client'
            ])
        },
        methods: {

            ...mapActions([
                'fetchVehicle',
                'fetchVehicleType',
                'fetchClient'
            ])
        }
    }
</script>
<style>
    #show-vehicle td:first-child{
        font-weight: bold;
    }
</style>