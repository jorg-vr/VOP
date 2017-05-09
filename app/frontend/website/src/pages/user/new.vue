<!--
    This page is used to create a new user.
-->
<template>
    <abstract-form :actions="actions" :object="user" :back="back" :resource="resource">
        <form-input :user="user"></form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './userFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.USER,
                user: {},
                back:{name:resources.USER.name.plural()}
            }
        },
        components: {
            abstractForm,formInput
        }
    }
</script>