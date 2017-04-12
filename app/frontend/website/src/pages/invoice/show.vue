<!--
    This page shows a certain client in detail. 
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("insurance.insurance") | capitalize }}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table" v-if="client.address">
                <tr>
                    <td>{{$t('invoice.payer') | capitalize }}</td>
                    <td>{{invoice.payer}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.beneficiary') | capitalize }}</td>
                    <td>{{invoice.beneficiary}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.type') | capitalize }}</td>
                    <td>{{invoice.type}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.paid') | capitalize }}</td>
                    <td>{{invoice.paid'}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.startDate') | capitalize }}</td>
                    <td>{{invoice.startDate}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.endDate') | capitalize }}</td>
                    <td>{{invoice.endDate}</td>
                </tr>
                <tr>
                    <td>{{$t('invoice.contracts') | capitalize }}</td>
                    <td>{{invoice.contracts}</td>
                </tr>
            </table>
            <h2>{{$t("invoice.contracts") | capitalize }}</h2>
            <list-component v-for="contract in contracts"
                            v-if="contract"
                            :object="contract"
                            :visibleKeys="new Array('name','companyName')"
                            show="contract"
                            :key="contract.id">
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
            id: String
        },
        created(){
            let clientId = this.id
            this.fetchClient({id: clientId})
            this.fetchFleetsByClient({clientId: clientId})
        },
        computed: {
            ...mapGetters([
                'client',
                'fleets'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchFleetsByClient'
            ])
        },
    }
</script>
