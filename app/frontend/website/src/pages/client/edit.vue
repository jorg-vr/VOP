<!--
    This page is used to edit a certain client.

    @param id: The id of the client which is edited.
-->
<template>
    <abstract-form-page :actions="actions" :id="id" :object="client" :resource="resource">
        <client-form-input :object="client"></client-form-input>
    </abstract-form-page>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractFormPage from '../../assets/form/AbstractFormPage.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import clientFormInput from '../../assets/form/types/clientFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.CLIENT,
                actions: actions.UPDATE,
                client: null
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
            abstractFormPage,clientFormInput
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

