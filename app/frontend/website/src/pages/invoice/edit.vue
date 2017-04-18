<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("invoice.invoice") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <invoice-form :successButtonText="$t('invoice.invoice').capitalize() + ' ' +  $t('actions_plural.create')" :failButtonText="$t('common.cancel').capitalize()"  :submit="updateInvoice"></invoice-form>
    </div>
</template>
<script>
    import invoiceForm from '../../assets/form/types/invoiceForm.vue'
    import {mapGetters, mapActions} from 'vuex'


    export default {
        components: {
            invoiceForm
        },
        props: {
            id: String
        },
        created(){
            this.fetchInvoice({id: this.id})
        },
        computed: {
            ...mapGetters([
                'invoice'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInvoice',
                'updateInvoice'
            ])
        }
    }
</script>

