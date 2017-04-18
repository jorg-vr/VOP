<!--
    This page is used to edit a certain user.

    @param id: The id of the user which is edited.


-->
<template>
    <user-form-page :actions="actions" :id="id"></user-form-page>
</template>
<script>
    import UserFormPage from '../../assets/form/pages/UserFormPage.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            UserFormPage
        },
        props: {
            id: String
        }
    }
</script>

