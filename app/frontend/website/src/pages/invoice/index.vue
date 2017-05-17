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

        <div v-if="invoices.length>0">
            <table class="table-hover table">
                <thead>
                <tr>
                    <th >
                        {{$t('invoice.startDate')| capitalize }}
                    </th>
                    <th >
                        {{$t('invoice.type')| capitalize }}
                    </th>
                    <th >
                        {{$t('invoice.totalAmount')| capitalize }}
                    </th>
                    <th >
                        {{$t('invoice.totalTax')| capitalize }}
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="invoice in invoices" class="list-tr">
                    <td  >
                        {{invoice.showableStartDate}}
                    </td>
                    <td  >
                        {{$t('invoiceTypes.'+invoice.type)}}
                    </td>
                    <td  >
                        €{{invoice.totalAmount}}
                    </td>
                    <td  >
                        €{{invoice.totalTax}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
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
            this.fetchInvoices({ids:{company: this.companyId}}).then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            ...mapGetters([
                'invoices'
            ])
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
