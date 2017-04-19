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
       <invoice-list-component v-for="invoice in filteredInvoices"
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
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import invoiceSearchBar from '../../assets/search/types/invoiceSearchBar.vue'

    export default {
        data(){
            return {
                resource: resources.INVOICE
            }
        },components: {
            invoiceListComponent, buttonLink, invoiceSearchBar
        },
        created() {
            this.addInvoicesCompany({companyId: "268061485634136044516596255853882581666", companyName: ""})
        },
        computed: {
            ...mapGetters([
                'invoices',
                'filteredInvoices',
                'getInvoiceByAll',
                'getInvoicesByAllAdvanced',
                // companies
                'clients',
            ])
        },
        methods: {
            ...mapActions([
                'fetchClients',
                'addInvoicesCompany',
            ]),
            ...mapMutations([
                'setFilteredClients'
            ]),
            // for search bar functionality
            updateInvoices(value){
                if(value!==''){
                    this.setFilteredInvoices(this.getInvoiceByAll(value))
                    console.log(value)
                    console.log(this.filteredInvoices)
                }
                else {
                    console.log('no search input')
                    this.setFilteredInvoices(this.invoices)
                }
            },
            updateInvoicesAdvanced(filterInvoice){
                this.setFilteredInvoices(this.getInvoicesByAllAdvanced(filterInvoice))

            }

        }
    }
</script>
