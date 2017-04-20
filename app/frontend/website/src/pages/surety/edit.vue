<!--
    This page is used to edit a certain insurance.

    @param id: the id of the insurance which is edited.
-->
<template>
    <insurance-form-page :actions="actions" :id="id"></insurance-form-page>
</template>
<script>
    import InsuranceFormPage from '../../assets/form/pages/InsuranceFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            InsuranceFormPage
        },
        props: {
            id: String
        }
    }
</script>

