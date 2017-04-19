<!--
    This page shows a certain client in detail. 
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("invoice.invoice") | capitalize }}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table">
                <tr>
                    <td>{{$t('invoice.payer') | capitalize }}</td>
                    <td>{{invoice.payer}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.beneficiary') | capitalize }}</td>
                    <td>{{invoice.beneficiary}}</td>
                </tr>
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
                    <td>{{$t('invoice.paid') | capitalize }}</td>
                    <td>{{invoice.paid}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.startDate') | capitalize }}</td>
                    <td>{{invoice.startDate}}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.endDate') | capitalize }}</td>
                    <td>{{invoice.endDate}}</td>
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
            console.log()
            let invoiceId = this.id
            let companyId= this.companyId
            // fetch information about this invoice
            this.fetchInvoice({id:invoiceId,companyId:companyId})
            // fetch all contracts for this company
            // TODO

        },
        computed: {
            ...mapGetters([
                'invoice',
                'insurances'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInvoice'
            ])
        },
    }
</script>
