<!--
    This page shows all contracts in the database or all contracts for a company
    From this page all concluded insurances in a contract can be viewed
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("contract.contract") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        
        <!-- Render an info-pane for every contract. Once all the data is loaded, the table will be shown.-->
        <!-- TODO ADD EXTRA FIELDS -->
        <list-component :resource="resource" :listObject="listObject">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import insuranceSearchBar from '../../assets/search/types/insuranceSearchBar.vue'

    export default {
        data(){
            return {
                resource: resources.CONTRACT
            }
        },
        components: {
            listComponent, buttonAdd, insuranceSearchBar
        },
        created() {
            this.setLoading({loading: true })
            if(this.authorizedForAll){
                this.fetchContracts().then(() => {
                    this.setLoading({loading: false })
                    console.log(this.contracts)
                })
            }
            else {
/*                 this.fetchContractsBy({filters: {company: this.activeFunction.company}}).then(() => {
                    this.setLoading({loading: false })
                })*/
            }
            // clear vehicle insurances 
            this.clearInsurances()


        },
        computed: {
            ...mapGetters([
                'contracts',
                'filteredContracts',
                'getContractsByAll',
                'getContractsByAllAdvanced',
                'activeFunction',
                'isAuthorizedForAllResources'
            ]),
            authorizedForAll(){
                return this.isAuthorizedForAllResources(this.resource, actions.READ_ALL)
            },
            listObject() {
                var listObj = {};
                listObj.headers = ['customerName','insuranceCompanyName','showableStartDate','totalCost','totalTax'];
                listObj.values = this.contracts;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchContractsBy',
                'fetchContracts',
                'deleteContract',
                // needed
                'fetchInsuranceByContract',
                'fetchInsurancesByCompany'
            ]),

            ...mapMutations([
                'setFilteredContracts',
                'setLoading',
                'clearInsurances'
            ]),

            // for search bar  
            updateContract(value){
                if(value!==''){
                    this.setFilteredContracts(this.getContractsByAll(value))
                }
                else {
                    this.setFilteredContracts(this.contracts)
                }
            },
            updateContractsAdvanced(filterContract){
                this.setFilteredContracts(this.getContractsByAllAdvanced(filterContract))
            }
        }
    }
</script>
