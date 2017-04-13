<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("client.client") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <client-form :action="actions.UPDATE" :oldClient="client"></client-form>
    </div>
</template>
<script>
    import ClientForm from '../../assets/form/types/clientForm.vue'
    import * as actions from '../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            ClientForm
        },
        props: {
            id: String
        },
        created(){
            this.fetchClient({id: this.id})
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

