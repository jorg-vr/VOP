<!--
    This page is used to edit a certain client.
-->
<template>
    <client-form-page :actions="actions" :id="id"></client-form-page>
</template>
<script>
    import ClientFormPage from '../../assets/form/pages/ClientFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            ClientFormPage
        },
        props: {
            id: String
        }
    }
</script>

