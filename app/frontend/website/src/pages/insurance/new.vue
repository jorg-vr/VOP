<!--
    This page is used to edit a certain insurance.
-->
<template>
    <insurance-form-page :actions="actions"></insurance-form-page>
</template>
<script>
    import InsuranceFormPage from '../../assets/form/pages/InsuranceFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.CREATE
            }
        },
        components: {
            InsuranceFormPage
        }
    }
</script>