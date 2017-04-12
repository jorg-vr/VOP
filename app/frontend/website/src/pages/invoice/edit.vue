<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("invoice.invoice") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <client-form :oldClient=client :submit="updateClient"></client-form>
    </div>
</template>
<script>
    import ClientForm from '../../assets/form/types/clientForm.vue'
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
                'updateClient'
            ])
        }
    }
</script>

