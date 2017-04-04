<!--
    This page is used to create a new fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1><h1>{{ $t("fleet.fleet") | capitalize }} {{$t("actions_plural.create") }}</h1></h1>
        </div>
        <fleet-form :submit="createFleet" :clientId="clientId"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../assets/form/types/fleetForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            FleetForm
        },
        props: {
            clientId: String
        },
        methods: {
            ...mapActions([
                'createFleet'
            ])
        }
    }
</script>
