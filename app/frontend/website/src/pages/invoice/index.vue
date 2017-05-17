<!--
    This page shows all invoices in the database.
    From this page a new invoice can be added or an existing invoice can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("invoice.invoices") | capitalize }}
            </h1>
        </div>

        <list-component :resource="resource" :listObject="listObject" :edit="false" :remove="false"></list-component>
        <button-back :route="{name: 'client',params:{id:companyId}}"></button-back>

    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        data(){
            return {
                resource: resources.INVOICE
            }
        },components: {
            listComponent,buttonBack
        },
        props: {
            companyId: String
        },
        created() {
            this.setLoading({loading: true })
            this.fetchInvoices({ids:{company: this.companyId}}).then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            ...mapGetters([
                'invoices'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["showableStartDate","type","totalAmount","totalTax"];
                listObj.values = this.invoices;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchInvoices',
            ]),
            ...mapMutations([
                'setLoading'
            ])

        }
    }
</script>
