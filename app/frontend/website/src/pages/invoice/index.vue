<!--
    This page shows all invoices in the database.
    From this page a new invoice can be added or an existing invoice can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("invoice.invoice") | capitalize }}
            </h1>
        </div>
       <invoice-list-component v-for="invoice in invoices"
                        v-if="invoice"
                        :object="invoice"
                        :resource="resource"
                        :visibleKeys="new Array('startDate','endDate','type','totalAmount')"
                        show="invoice"
                        :key="invoice.id">
        </invoice-list-component>

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

    export default {
        data(){
            return {
                resource: resources.INVOICE
            }
        },components: {
            invoiceListComponent
        },
        created() {
            this.fetchInvoicesByCompany({companyId: "268061485634136044516596255853882581666", companyName: ""})
        },
        computed: {
            ...mapGetters([
                'invoices',
                // companies
                'clients',
            ])
        },
        methods: {
            ...mapActions([
                'fetchClients',
                'fetchInvoicesByCompany',
            ]),
            ...mapMutations([
                'setFilteredClients'
            ])

        }
    }
</script>
