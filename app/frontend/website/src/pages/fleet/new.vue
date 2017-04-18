<!--
    This page is used to create a new fleet.
-->
<template>
    <fleet-form-page :actions="actions"></fleet-form-page>
</template>
<script>
    import FleetFormPage from '../../assets/form/pages/FleetFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.CREATE
            }
        },
        components: {
            FleetFormPage
        }
    }
</script>