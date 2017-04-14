<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <client-form :actions="actions" :oldClient="client"></client-form>
    </div>
</template>
<script>
    import ClientForm from '../../../assets/form/types/clientForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('client', this.actions.name)
            }
        },
        components: {
            ClientForm
        },
        created(){
            if(this.id){
                this.fetchClient({id: this.id})
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'client'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClient',
            ])
        }
    }
</script>

