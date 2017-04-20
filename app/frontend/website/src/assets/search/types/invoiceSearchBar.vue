<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(invoice)" @reset="$emit('search', '')"  :title="$t('invoice.invoices')">
        <invoice-form-input :invoice="invoice"></invoice-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import invoiceFormInput from '../../form/types/invoiceFormInput.vue'
    export default {
        data() {
            return {
                invoice: {}
            }
        },
        components: {
            searchBar, invoiceFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
