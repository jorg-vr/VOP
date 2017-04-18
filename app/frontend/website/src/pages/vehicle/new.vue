<!--
    This page is used to create a new vehicle.

    @param fleetId (optional): The fleetId of  the new vehicle
-->
<template>
    <vehicle-form-page :actions="actions" :fleetId="fleetId"></vehicle-form-page>
</template>
<script>
    import VehicleFormPage from '../../assets/form/pages/VehicleFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.CREATE
            }
        },
        props: {
            fleetId: String
        },
        components: {
            VehicleFormPage
        }
    }
</script>