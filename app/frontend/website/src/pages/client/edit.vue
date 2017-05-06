<!--
    This page is used to edit a certain client.

    @param id: The id of the client which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="client" :back="back" :resource="resource">
        <form-input :object="client"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from '../../assets/form/types/ClientFormInput.vue'

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
                this.fetchClient({id: this.id}).then(o => {
                    this.client = o;
                })
            }
        },
        components: {
            abstractForm,formInput
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

