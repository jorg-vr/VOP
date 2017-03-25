<!--
    This page is used to create a new fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1><h1>{{$t("common.new") | capitalize}} {{$t("fleet.fleet")}}</h1></h1>
        </div>
        <fleet-form :submit="createFleet" :fleet={}></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../assets/form/types/fleetForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            FleetForm
        },
        methods: {
            ...mapActions([
                'createFleet'
            ])
        }
    }
</script>
