<!--
    This page is used to create a new insurance.
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