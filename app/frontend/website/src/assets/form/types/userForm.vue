<!--
    This page is used to generate a form for a user.
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
                    return {address: {}}
                }
                else {
                    return this.oldUser
                }
            }
        },
        methods: {
            proceed(){
                this.$store.dispatch(action + 'User').then(() => {
                    this.submit(this.user).then(() => {
                        this.$router.push({name: 'users'})
                    })
                })

            }
        }
    }
</script>
