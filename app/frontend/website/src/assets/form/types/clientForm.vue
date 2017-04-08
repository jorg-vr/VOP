<!--
    This page is used to generate a form for a user.
-->
<template>
    <form-component v-if="client" @submit="proceed" :failroute="{name: 'clients'}" :successButtonText="successButtonText" :failButtonText="failButtonText">
        <client-form-input v-if="client.address" :client="client"></client-form-input>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import clientFormInput from './clientFormInput.vue'

    export default {
        components: {
            formComponent, clientFormInput
        },
        props: {
            failButtonText: String,
            successButtonText: String,
            submit: Function, //Function to create the fleet.
            oldClient: Object
        },
        computed: {
            client(){
                if(this.oldClient === undefined) {
                    return {address: {}}
                }
                else {
                    return this.oldClient
                }
            }
        },
        methods: {
            proceed(){
                this.submit({client: this.client}).then(() => {
                    this.$router.push({name: 'clients'})
                })
            }
        }
    }
</script>
