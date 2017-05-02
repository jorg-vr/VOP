<!--
This page is used to edit or create a certain client.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <form-component v-if="client" :actions="actions" :resource="resource" :object="client">
            <client-form-input :client="client"></client-form-input>
        </form-component>
    </div>
</template>
<script>
    import ClientForm from '../../../assets/form/types/clientForm.vue'
    import actions from '../../../constants/actions'
    import {mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('client', this.actions.name),
                client: null
            }
        },
        components: {
            ClientForm
        },
        created(){
            if(this.id){
                this.fetchClient({id: this.id}).then(client => {
                    if(client === null) {
                        this.client= {address: {}};
                    }
                    else {
                        this.client = client;
                    }
                })
            }
        },
        props: {
            resource: Object,
            id: String,
            actions: Object //The action for this form.
        },
        methods: {
            ...mapActions([
                'fetchClient'
            ])
        }
    }
</script>