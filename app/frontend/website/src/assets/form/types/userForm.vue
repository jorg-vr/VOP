<!--
    This is a form to create/update a user.
-->
<template>
    <div>
        <form-component v-if="user" @submit="proceed" :failroute="{name: 'users'}" :successButtonText="successButtonText" :failButtonText="failButtonText">
            <user-form-input :user="user"></user-form-input>
        </form-component>
    </div>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import userFormInput from './userFormInput.vue'

    export default {
        components: {
            formComponent, userFormInput
        },
        props: {
            failButtonText: String,
            successButtonText: String,
            submit: Function, //Function to create the fleet.
            oldUser: Object
        },
        computed: {
            user(){
                if(this.oldUser === undefined) {
                    return {address: {}}

                }
                else {
                    return this.oldUser
                }
            }
        },
        methods: {
            proceed(){
                this.submit(this.user).then(() => {
                    this.$router.push({name: 'users'})
                })
            }
        }
    }
</script>
