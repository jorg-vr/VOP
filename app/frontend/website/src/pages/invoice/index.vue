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

        <list-component :objects="invoices" :resource="resource" :visibleKeys="['showableStartDate', 'showableEndDate', 'totalAmount']">
        </list-component>
        <button-back :route="{name: 'client'}"></button-back>

    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from '../../assets/list/listComponent.vue'
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
            this.fetchInvoicesByCompany({companyId: this.companyId}).then(() => {
                this.setLoading({loading: false })
            })
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
                'setFilteredClients',
                'setLoading'
            ])

        }
    }
</script>
