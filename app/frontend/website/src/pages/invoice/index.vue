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

        <list-component v-if="show" :resource="resource" :listObject="listObject" :edit="false" :remove="false"></list-component>
        <button-back :route="{name: 'client',params:{id:companyId}}"></button-back>

    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import {translateInvoiceTypes,centsToEuroArray} from '../../utils/utils'

    export default {
        data(){
            return {
                resource: resources.INVOICE,
                show:false
            }
        },components: {
            listComponent,buttonBack
        },
        props: {
            companyId: String
        },
        created() {
            this.setLoading({loading: true });
            this.fetchInvoices({ids:{company: this.companyId}}).then(() => {
                translateInvoiceTypes(this.invoices);
                centsToEuroArray(this.invoices,"totalAmount");
                centsToEuroArray(this.invoices,"totalTax");
                this.show=true;
                this.setLoading({loading: false })
            });
            this.fetchClient({id:this.companyId});
        },
        computed: {
            ...mapGetters([
                'invoices',
                'client'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["showableStartDate","invoiceTypeTranslation","totalAmountEuro","totalTaxEuro"];
                listObj.values = this.invoices;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchInvoices',
                'fetchClient'
            ]),
            ...mapMutations([
                'setLoading'
            ])

        }
    }
</script>
