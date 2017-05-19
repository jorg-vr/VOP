<!--
    This page shows a certain client in detail.

    @param id: The id of the client to be shown.

-->
<template>
    <div v-if="client && client.address" class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{client.name| capitalize }}
                <button-link v-if="hasPermissionForRoute('invoices')"
                        buttonClass="pull-right btn btn-primary btn-add" :route="{name: 'invoices', params: {companyId: id}}">
                    {{$t("invoice.invoices") | capitalize }}
                </button-link>
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
                    <td>{{country}}</td>
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
            <commissions  v-if="client.type===clientTypes.CUSTOMER.type" :id="id" loc="companies"  ></commissions>

            <h2 class="col-md-12" v-if="client.type===clientTypes.CUSTOMER.type" >{{$t("fleet.fleets") | capitalize }}
                <button-add :resource="resources.FLEET" :params="{clientId: client.id}"></button-add>
            </h2>
            <list-component v-if="client.type===clientTypes.CUSTOMER.type&&listObjectFleets.values.length>0" :listObject="listObjectFleets" :resource="resources.FLEET">
            </list-component>

            <h2 class="col-md-12" v-if="client.type===clientTypes.INSURANCE_COMPANY.type" >{{$t("surety.sureties") | capitalize }}
                <button-add :resource="resources.SURETY" :params="{clientId: client.id}"></button-add>
            </h2>
            <list-component v-if="client.type===clientTypes.INSURANCE_COMPANY.type&&show&&listObjectSureties.values.length>0" :listObject="listObjectSureties" :resource="resources.SURETY">
            </list-component>

            <h2>{{$t("contract.contracts") | capitalize}}</h2>
            <list-component v-if="show&&listObjectContracts.values.length>0" :resource="resources.CONTRACT" :listObject="listObjectContracts"></list-component>
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
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import periods from '../../constants/periods'
    import commissions from '../commission/collapse.vue'
    import {translateSuretyTypes,centsToEuroArray} from '../../utils/utils'
    import countries from './countries'
    import Vue from 'vue'

    export default {
        data(){
            return {
                resources:resources,
                type: '',
                clientTypes: clientTypes,
                show:false
            }
        },
        components: {
            buttonBack, listComponent, buttonAdd, buttonLink,commissions
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
                    this.type = clientTypes[client.type].translation();
                    if(client.type===clientTypes.CUSTOMER.type){
                        this.fetchFleetsBy({filters: {company: clientId}});
                        this.fetchContractsBy({filters: {customer: clientId}}).then(()=>{
                            centsToEuroArray(this.contracts,"totalCost");
                            centsToEuroArray(this.contracts,"totalTax");
                            this.show=true;
                        });
                    }
                    if(client.type===clientTypes.INSURANCE_COMPANY.type){
                        let p1=this.fetchContractsBy({filters: {insuranceCompany: clientId}});
                        let p2=this.fetchSureties({ids:{company:clientId}});
                        Promise.all([p1,p2]).then(()=>{
                            centsToEuroArray(this.contracts,"totalCost");
                            centsToEuroArray(this.contracts,"totalTax");
                            centsToEuroArray(this.sureties,"premium");
                            this.sureties=translateSuretyTypes(this.sureties);
                            this.show=true;
                        });
                    }
                }
                this.setLoading({loading: false })
            });

        },
        computed: {
            ...mapGetters([
                'hasPermissionForRoute',
                'client',
                'contracts',
                'fleets',
                'sureties'
            ]),
            country(){
                if(this.client.address){
                    let code = this.client.address.country
                    let country = countries[Vue.config.lang].filter(country => country.code === code)[0]
                    return country.name
                }
                else {
                    return ''
                }
            },
            listObjectFleets () {
                var listObj = {};
                listObj.headers = ["name"];
                listObj.values = this.fleets;
                return listObj;
            },
            listObjectContracts() {
                var listObj = {};
                listObj.headers = ['insuranceCompanyName','showableStartDate','totalCostEuro','totalTaxEuro'];
                listObj.values = this.contracts;
                return listObj;
            },
            listObjectSureties() {
                var listObj = {};
                listObj.headers = ['suretyTypeTranslation','premiumEuro'];
                listObj.values = this.sureties;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchFleetsBy',
                'fetchContractsBy',
                'fetchSureties'
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
