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
        <log :resourceId="resourceId" :id="id" :entry="entry" resource="vehicles"></log>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import log from '../log/show.vue'

    export default {
        data() {
            return {
                vehicle: {}
            }
        },
        components: {
            log
        },
        props: {
            id: String,
            resourceId: String,
            entry: Object
        },
        created(){
            this.fetchVehicle({id: this.resourceId}).then(vehicle => {
                this.vehicle = vehicle
            })
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
            ])
        },
    }
</script>
