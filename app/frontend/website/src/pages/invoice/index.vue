<!--
    This page shows all invoices in the database.
    From this page a new invoice can be added or an existing invoice can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("invoice.invoice") | capitalize }}
                <button-link :route="{name: 'new_invoice'}" buttonClass="pull-right btn btn-md btn-primary btn-add">
                    {{$t("common.add") | capitalize }} {{$t("invoice.invoice")}}
                </button-link>
            </h1>
        </div>
        <client-search-bar @search="updateInvoices" @advancedSearch="updateInvoicesAdvanced"></client-search-bar>
        <!-- Render an info-pane for every invoice. Once all the data is loaded, the table will be shown.-->
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
            var i
            //var fleetsArray = [{company: 'bedrijf1', facturationPeriod:'3',id:'55',name:'fleetbedrijf1',paymentPeriod:'1'},{company: 'bedrijf2', facturationPeriod:'3',id:'56',name:'fleetbedrijf2',paymentPeriod:'1'}]
            //var invoiceArray = [{companyName:'bedrijf1', companyId:'1', fleetName: 'fleetbedrijf1', fleetId: '55' ,endDate:'2016-08-29',fleet:'55',id:'75',paid:'false',startDate:'2016-08-01',totalAmount:'44',type:'billing'}]
            // LOCAL TESTING
            // add test companies
            //this.setClients([{name:'bedrijf1',id:'1'},{name: 'bedrijf2',id:'2'}])
            // add test invoices
            //this.setFilteredInvoices(invoiceArray)
            // for show.vue
            //this.setInvoice({companyName:'bedrijf1', companyId:'1',endDate:'2016-08-29',id:'75',paid:'false',startDate:'2016-08-01',totalAmount:'44',type:'billing'})



            /* List all invoices
                fetch companies
                for each company, fetch all invoices
            */
            this.fetchClients().then(clients => {
                this.setFilteredClients(clients)
            })
            for (i = 0; i < this.clients.length; i++) { 
                // Add all invoices from company to list of invoices
                this.addInvoicesCompany({companyId: this.clients[i].id, companyName: this.clients[i].name})
                console.log('for client:'+this.clients[i].id)                
            }    
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
