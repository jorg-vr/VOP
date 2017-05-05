<!--
    This page is used to edit a certain client.

    @param id: The id of the client which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="client" :back="back" :resource="resource">
        <client-form-input :object="client"></client-form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import clientFormInput from '../../assets/form/types/clientFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.CLIENT,
                actions: actions.UPDATE,
                client: {address:{}},
                back:{name:resources.CLIENT.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchClient({id: this.id}).then(cl => {
                    this.client = cl;
                })
            }
        },
        components: {
            abstractForm,clientFormInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchClient'
            ])

        }
    }
</script>

