<!--
    This page shows all contracts in the database or all contracts for a company
    From this page all concluded insurances in a contract can be viewed
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
            <div class="page-header">
                <h1>
                    {{$t("contract.contracts") | capitalize}}
                    <button-add :resource="resource"></button-add>
                </h1>
            </div>

        <protected-element :permission="permission">
        <abstract-search-form :resource="resource" :filters="filters">
            <contract-search-input :contract="filters"></contract-search-input>
        </abstract-search-form>
        </protected-element>

        <!-- Render an info-pane for every contract. Once all the data is loaded, the table will be shown.-->
        <list-component v-if="show" :resource="resource" :listObject="listObject">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import ContractSearchInput from './ContractSearchInput.vue'
    import {centsToEuroArray} from '../../utils/utils'
    import ProtectedElement from '../../assets/protection/ProtectedElement.js'

    export default {
        data(){
            return {
                permission: {
                    resource: resources.CONTRACT,
                    actions: actions.CREATE
                },
                filters: {},
                resource: resources.CONTRACT,
                show:false
            }
        },
        components: {
            listComponent, buttonAdd, ContractSearchInput, AbstractSearchForm,ProtectedElement
        },
        created() {
            this.setLoading({loading: true })
            if(this.isAuthorizedForAllResources(this.resource, actions.READ_ALL)) {
                this.fetchContracts().then(() => {
                    centsToEuroArray(this.contracts, "totalCost");
                    centsToEuroArray(this.contracts, "totalTax");
                    this.setLoading({loading: false});
                    this.show = true;
                })
            }else{
                this.fetchContractsBy({filters: {customer: this.activeFunction.company}}).then(() => {
                    centsToEuroArray(this.contracts, "totalCost");
                    centsToEuroArray(this.contracts, "totalTax");
                    this.setLoading({loading: false});
                    this.show = true;
                })
            }
        },
        computed: {
            ...mapGetters([
                'contracts',
                'activeFunction',
                'isAuthorizedForAllResources'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['customerName','insuranceCompanyName','showableStartDate','totalCostEuro','totalTaxEuro'];
                listObj.values = this.contracts;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchContracts',
                'fetchContractsBy'
            ]),

            ...mapMutations([
                'setLoading'
            ]),
        }
    }
</script>
