<!--
    This page is used to create a new client.
-->
<template>
    <abstract-form :actions="actions" :object="client" :back="back" :resource="resource">
        <client-form-input :object="client"></client-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import clientFormInput from '../../assets/form/types/ClientFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.CLIENT,
                client: {address:{}},
                back:{name:resources.CLIENT.name.plural()}
            }
        },
        components: {
            abstractForm,clientFormInput
        }
    }
</script>