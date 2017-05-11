<!--
    This page shows all insurances from a contract
    @param id: The id of the contract to be shown.

-->
<template>
    <div>
        <div class="page-header">
            <h1>
                {{$t("insurance.types") | capitalize }}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <insurance-search-bar @search="updateContractInsurances" @advancedSearch="updateContractInsurancesAdvanced"></insurance-search-bar>
        <list-component :resource="resource" :listObject="listObject"></list-component>
        <button-back :route="{name: 'insurances'}"></button-back>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/list/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import insuranceSearchBar from '../../assets/search/types/insuranceSearchBar.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        data(){
            return {
                resource: resources.SURETY
            }
        },
        components: {
            buttonBack,buttonAdd,listComponent,buttonLink,insuranceSearchBar
        },
        props: {
            id: String
        },
        created(){
            // get contract id 
            let contractId = this.id
            this.setLoading({loading: true })
            // get all insurances from the contract with contract Id
            this.fetchInsurancesByContract(contractId).then(() => {
                this.setLoading({loading: false })
            })
            // set value in contractId (used for API calls to contracts/id/insurances/)
            this.setContractId(contractId)
        },
        computed: {
            ...mapGetters([
            'insurance',
            'contractInsurances',
            'filteredcontractInsurances',
            'contractId',
            'getContractInsurancesByAll',
            'getContractInsurancesByAllAdvanced'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['cost','tax','showableStartDate'];
                listObj.values = this.filteredcontractInsurances;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchInsurancesByContract'
            ]),
            ...mapMutations([
                'setContractId',
                'setFilteredcontractInsurances',
                'setLoading'
            ]),
            updateContractInsurances(value){
                console.log(value)
                if(value!==''){
                    this.setFilteredcontractInsurances(this.getContractInsurancesByAll(value))
                }
                else {
                    this.setFilteredcontractInsurances(this.contractInsurances)
                }
            },
            updateContractInsurancesAdvanced(filterInsurance){
                this.setFilteredInsurances(this.getContractInsurancesByAllAdvanced(filterInsurance))
            }
        },
    }
</script>
