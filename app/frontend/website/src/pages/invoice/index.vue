<!--
    This page shows all invoices in the database.
    From this page a new invoice can be added or an existing invoice can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("invoice.invoices") | capitalize }} {{client.name}}
            </h1>
        </div>
       <invoice-list-component v-for="invoice in invoices"
                        v-if="invoice"
                        :object="invoice"
                        :resource="resource"
                        show="invoice"
                        :key="invoice.id">
        </invoice-list-component>
        <button-back :route="{name: 'client'}"></button-back>

    </div>
</template>
<style>
.btn-add {
    margin-top: -2px;
}
</style>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import invoiceListComponent from "../../assets/general/invoiceListComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        data(){
            return {
                resource: resources.INVOICE
            }
        },components: {
            invoiceListComponent,buttonBack
        },
        props: {
            companyId: String
        },
        created() {
            this.companyId=this.client.id //TODO is this good practice?
            this.fetchInvoicesByCompany({companyId: this.companyId})
        },
        computed: {
            ...mapGetters([
                'invoices',
                'clients',
                'client'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClients',
                'fetchClient',
                'fetchInvoicesByCompany',
            ]),
            ...mapMutations([
                'setFilteredClients'
            ])

        }
    }
</script>
