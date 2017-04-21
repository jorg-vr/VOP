<!--
    This page shows all insurances from a contract
    @param id: The id of the contract to be shown.

-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("insurance.types") | capitalize }} </h1>
            <button-add :resource="resource"></button-add>
        </div>
        <insurance-search-bar @search="updateContractInsurances" @advancedSearch="updateContractInsurancesAdvanced"></insurance-search-bar>
        <list-component :resource="resource"
                        :objects="filteredcontractInsurances"
                        :visibleKeys="['cost','tax','startDate']">
        </list-component>
        <button-back :route="{name: 'insurances'}"></button-back>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/list/listItem.vue'
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
            // get all insurances from the contract with contract Id
            this.fetchInsurancesByContract(contractId)
            // set value in contractId (used for API calls to contracts/id/insurances/)
            this.setContractId(contractId)
            console.log(this.filteredcontractInsurances)
            // test
            console.log(this.contractId)
        },
        computed: {
            ...mapGetters([
                'insurance',
                'contractInsurances',
                'filteredcontractInsurances',
                'contractId',
                'getContractInsurancesByAll',
                'getContractInsurancesByAllAdvanced'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInsurancesByContract'
            ]),
            ...mapMutations([
                'setContractId',
                'setFilteredcontractInsurances'

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