<!--
    This page shows a certain client in detail. 
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("invoice.invoice") | capitalize }} {{client.name}}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table">
                <tr>
                    <td>{{$t('invoice.type') | capitalize }}</td>
                    <td>{{invoice.type}}</td>
                </tr>
                 <tr>
                    <td>{{$t('invoice.totalAmount') | capitalize }}</td>
                    <td>€ {{invoice.totalAmount}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.totalAmount') | capitalize }}</td>
                    <td>€ {{invoice.totalTax}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.startDate') | capitalize }}</td>
                    <td>{{showDate(invoice.startDate)}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.endDate') | capitalize }}</td>
                    <td>{{showDate(invoice.endDate)}}</td>
                </tr>
            </table>
            <h2>{{$t("invoice.contracts") | capitalize }}</h2>
            <list-component v-for="insurance in insurances"
                            v-if="insurance"
                            :object="insurance"
                            :visibleKeys="new Array('insuranceCompany','type','vehicle')"
                            show="insurance"
                            :key="insurance.id">
            </list-component>
            <button-back :route="{name: 'invoices'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        components: {
            buttonBack, listComponent, buttonAdd
        },
        props: {
            id: String,
            companyId: String
        },
        created(){
            console.log();
            let invoiceId = this.id;
            let companyId= this.companyId;
            // fetch information about this invoice
            this.fetchInvoice({id:invoiceId,companyId:companyId});

            this.fetchClient({id: companyId});

            this.fetchInsurancesByInvoice({id:invoiceId,companyId:companyId});

        },
        computed: {
            ...mapGetters([
                'client',
                'invoice',
                'insurances'
            ])
        },
        methods: {
            ...mapActions([
                    'fetchInvoice',
                    'fetchClient',
                    'fetchInsurancesByInvoice'
            ]),
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            }
        },
    }
</script>
