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
       <list-component v-for="invoice in filteredInvoices"
                        v-if="invoice"
                        :object="invoice"
                        :visibleKeys="new Array('companyName','startDate','endDate','type','totalAmount')"
                        show="invoice"
                        :key="invoice.id">
        </list-component>

    </div>
</template>
<style>
.btn-add {
    margin-top: -2px;
}
</style>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import invoiceSearchBar from '../../assets/search/types/invoiceSearchBar.vue'

    export default {
        components: {
            listComponent, buttonLink, invoiceSearchBar
        },
        created() {
            this.addInvoicesCompany({companyId: "314638493889344791017097203248093678568", companyName: ""})
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
