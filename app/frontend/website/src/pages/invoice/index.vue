<!--
    This page shows all invoices in the database.
    From this page a new invoice can be added or an existing invoice can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("invoice.invoices") | capitalize }} <span v-if="client">{{client.name}}</span>
            </h1>
        </div>
        <list-component :objects="invoices" :resource="resource" :listObject="listObject">
        </list-component>
        <button-back :route="{name: 'client'}"></button-back>

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
            this.fetchClient({id: this.companyId})
            this.fetchInvoicesByCompany({ filters: {companyId: this.companyId}).then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            listObject() {
                let listObj = {};
                listObj.headers = ['showableStartDate', 'showableEndDate', 'totalAmount'];
                listObj.values = this.invoices;
                return listObj;
            },
            ...mapGetters([
                'invoices',
                'client'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchInvoicesBy',
            ]),
            ...mapMutations([
                'setLoading'
            ])
        }
    }
</script>
