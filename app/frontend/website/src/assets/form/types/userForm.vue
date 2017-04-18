<!--
This page is used to show a form for a user.

@param actions: the action this form is intended for (create/update)
@param oldUser (optional): The old user if the form is intended to a update user
-->
<template>
    <form-component v-if="user" :actions="actions" :resource="resource" :object="user">
        <user-form-input :user="user"></user-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import userFormInput from './userFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.USER,
            }
        },
        components: {
            formComponent, userFormInput
        },
        props: {
            actions: Object,
            oldUser: Object
        },
        computed: {
            user(){
                if(this.oldUser === null) {
                    return {}
                }
                else {
                    return this.oldUser
                }
            }
        }
    }
</script>
