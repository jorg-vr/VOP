<!--
    This page is used to edit a certain vehicle.

    @param id: The id of the vehicle which is edited.
-->
<template>
    <vehicle-form-page :actions="actions" :id="id"></vehicle-form-page>
</template>
<script>
    import VehicleFormPage from '../../assets/form/pages/VehicleFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            VehicleFormPage
        },
        props: {
            id: String
        }
    }
</script>

