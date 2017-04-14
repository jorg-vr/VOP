<!--
    This page is used to edit a certain user.
-->
<template>
    <user-form-page :actions="actions"></user-form-page>
</template>
<script>
    import UserFormPage from '../../assets/form/pages/UserFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.CREATE
            }
        },
        components: {
            UserFormPage
        }
    }
</script>