<!--
This page is used to show a form for a client.

@param actions: the action this form is intended for (create/update)
@param oldClient (optional): The old client if the form is intended to a update client
-->
<template>
    <form-component v-if="client" :actions="actions" :resource="resource" :object="client">
        <client-form-input :client="client"></client-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import clientFormInput from './clientFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.CLIENT,
            }
        },
        components: {
            formComponent, clientFormInput
        },
        props: {
            actions: Object,
            oldClient: Object
        },
        computed: {
            client(){
                if(this.oldClient === null) {
                    return {address: {}}
                }
                else {
                    return this.oldClient
                }
            }
        }
    }
</script>
