<!--
    This page is used to edit a certain fleet.
-->
<template>
    <fleet-form-page :actions="actions" :id="id"></fleet-form-page>
</template>
<script>
    import FleetFormPage from '../../assets/form/pages/FleetFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            FleetFormPage
        },
        props: {
            id: String
        }
    }
</script>

