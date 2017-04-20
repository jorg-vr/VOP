<!--
    This is a form to create/update an invoice.
-->
<template>
    <div>
        <form-component v-if="invoice" @submit="proceed" :failroute="{name: 'invoices'}" :successButtonText="successButtonText" :failButtonText="failButtonText">
            <invoice-form-input :invoice="invoice"></invoice-form-input>
        </form-component>
    </div>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import invoiceFormInput from './invoiceFormInput.vue'

    export default {
        components: {
            formComponent, invoiceFormInput
        },
        props: {
            failButtonText: String,
            successButtonText: String,
            submit: Function, //Function to create the fleet.
            oldInvoice: Object
        },
        computed: {
            invoice(){
                if(this.oldInvoice === undefined) {
                    return {address: {}}

                }
                else {
                    return this.oldInvoice
                }
            }
        },
        methods: {
            proceed(){
                this.submit({invoice: this.invoice}).then(() => {
                    this.$router.push({name: 'invoices'})
                })
            }
        }
    }
</script>
