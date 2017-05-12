<!--
    This page shows a certain client in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>
                {{$t("log.logEntry") | capitalize}} {{$t("vehicle.vehicle")}} {{vehicle.licensePlate}}
            </h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table">
                <tr>
                    <td>{{$t('log.action') | capitalize }}</td>
                    {{$t('log.' + entry.action.toLowerCase()) | capitalize}}
                </tr>
                <tr>
                    <td>{{$t('log.resource') | capitalize }}</td>
                    {{$t('resource.' + entry.resource.toLowerCase()) | capitalize}}
                </tr>
                <tr>
                    <td>{{$t('log.dateTime') | capitalize }}</td>
                    <td>{{entry.dateTime.showableDateTime()}}</td>
                </tr>
            </table>
            <div v-if="entry.description.length > 0">
                <h2>{{$t('common.description') | capitalize }}</h2>
                <table  class="table show-table">
                    <tr>
                        <th>{{$t('description.property') | capitalize}}</th>
                        <th>{{$t('description.oldValue') | capitalize}}</th>
                        <th>{{$t('description.newValue') | capitalize}}</th>
                    </tr>
                    <tr v-for="description in entry.description">
                        <td>{{$t(entry.resource.toLowerCase() + '.' + description.field) | capitalize}}</td>
                        <td>{{description.oldValue}}</td>
                        <td>{{description.newValue}}</td>
                    </tr>
                </table>
            </div >

            <button-back :route="{name: 'vehicle_logs', params: {id: this.vehicleId}}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        data() {
            return {
                log: {},
                vehicle: {}
            }
        },
        components: {
            buttonBack
        },
        props: {
            id: String,
            vehicleId: String,
            entry: Object
        },
        created(){
            console.log(this.entry)
            this.fetchVehicle({id: this.vehicleId}).then(vehicle => {
                this.vehicle = vehicle
            })
            this.fetchVehicleLog({vehicleId: this.vehicleId, id: this.id}).then(log => {
                this.log = log
            })
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
                'fetchVehicleLog',
            ])
        },
    }
</script>
