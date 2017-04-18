<!--
    This page is used to generate an invoice for a certain company
-->
<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1>{{ $t("invoice.invoice") | capitalize }} {{$t("actions_plural.create") }}</h1>
     </div>  
     <invoice-form :successButtonText="$t('invoice.invoice').capitalize() + ' ' +  $t('actions_plural.create')" :failButtonText="$t('common.cancel').capitalize()"  :submit="createInvoice"></invoice-form>
 </div>
</template>
<script>
    import invoiceForm from '../../assets/form/types/invoiceForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            invoiceForm
        },
        methods: {
            ...mapActions([
                'createInvoice'
            ])
        }
    }
</script>

