<!--
    This page shows a certain client in detail.

    @param id: The id of the client to be shown.

-->
<template>
    <div v-if="client && client.address" class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{client.name| capitalize }}
                <button-invoice  :id="client.id" ></button-invoice>
            </h1>

        </div>
        <div >
            <table class="table show-table" v-if="client.address">
                <tr>
                    <td v-if="type!==''">Type</td>
                    <td>{{type | capitalize}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.country') | capitalize }}</td>
                    <td>{{client.address.country}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.city') | capitalize }}</td>
                    <td>{{client.address.city}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.postalCode') | capitalize }}</td>
                    <td>{{client.address.postalCode}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.street') | capitalize }}</td>
                    <td>{{client.address.street}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.houseNumber') | capitalize }}</td>
                    <td>{{client.address.houseNumber}}</td>
                </tr>
                <tr v-if="client.vatNumber">
                    <td>{{$t('client.vatNumber') | capitalize }}</td>
                    <td>{{client.vatNumber}}</td>
                </tr>
                <tr>
                    <td>{{$t('client.phoneNumber') | capitalize }}</td>
                    <td>{{client.phoneNumber}}</td>
                </tr>
                <tr v-if="client.facturationPeriod">
                    <td>{{$t('client.facturationPeriod') | capitalize }}</td>
                    <td>{{getPeriodText(client.facturationPeriod) | capitalize}}</td>
                </tr>
                <tr v-if="client.paymentPeriod">
                    <td>{{$t('client.paymentPeriod') | capitalize }}</td>
                    <td>{{getPeriodText(client.paymentPeriod) | capitalize}}</td>
                </tr>
            </table>
            <commissions  v-if="client.type===clientTypes.CUSTOMER.type" :id="id" loc="companies" :back="back" ></commissions>
            <h2 class="col-md-12" v-if="client.type===clientTypes.CUSTOMER.type" >{{$t("fleet.fleets") | capitalize }}
                <button-add :resource="resource" :params="{clientId: client.id}"></button-add>
            </h2>
            <list-component v-if="listObject.values.length>0" :listObject="listObject" :resource="resource">
            </list-component>
            <h2>{{$t("contract.contracts") | capitalize}}</h2>
            <list-component v-if="listObject2.values.length>0" :resource="resource2" :listObject="listObject2"></list-component>
            <button-back :route="{name: 'clients'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapMutations, mapGetters, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import clientTypes from '../../constants/clientTypes'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonInvoice from '../../assets/buttons/buttonInvoice.vue'
    import periods from '../../constants/periods'
    import commissions from '../commission/collapse.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET,
                resource2: resources.CONTRACT,
                type: '',
                clientTypes: clientTypes,
                back:{name:resources.CLIENT.name,params:{id:this.id}}
            }
        },
        components: {
            buttonBack, listComponent, buttonAdd, buttonInvoice,commissions
        },
        props: {
            id: String
        },
        created(){
            this.$store.commit('clearFleets')
            this.$store.commit('clearContracts')
            this.setLoading({loading: true})
            let clientId = this.id
            this.fetchClient({id: clientId}).then(client => {
                if(client.type){
                    this.type = clientTypes[client.type].translation()
                    if(client.type===clientTypes.CUSTOMER.type){
                        this.fetchFleetsBy({filters: {company: clientId}});
                        this.fetchContractsBy({filters: {customer: clientId}})
                    }
                    if(client.type===clientTypes.INSURANCE_COMPANY.type){
                        this.fetchContractsBy({filters: {insuranceCompany: clientId}})
                    }
                }
                this.setLoading({loading: false })
            });

        },
        computed: {
            ...mapGetters([
                'client',
                'contracts',
                'fleets'
            ]),
            listObject () {
                var listObj = {};
                listObj.headers = ["name"];
                listObj.values = this.fleets;
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['insuranceCompanyName','showableStartDate','totalCost','totalTax'];
                listObj.values = this.contracts;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchFleetsBy',
                'fetchContractsBy'
            ]),
            ...mapMutations([
                'setLoading'
            ]),
            getPeriodText(period){
                let periodObj =  periods.filter(periodObj => periodObj.period === period)[0]
                return periodObj ? periodObj.translation() : ''
            }
        }
    }
</script>
