<!--
    This page shows all contracts in the database.
    From this page all invoices from a contract can be viewed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("insurance.insurance") | capitalize}}
            </h1>
        </div>
        <insurance-search-bar @search="updateInsurance" @advancedSearch="updateInsurancesAdvanced"></insurance-search-bar>
        <!-- Render an info-pane for every insurance. Once all the data is loaded, the table will be shown.-->
        <list-component :resource="resource" :objects="filteredInsurances" :visibleKeys="['name','startDate','totalCost','totalTax']">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listItem.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import insuranceSearchBar from '../../assets/search/types/insuranceSearchBar.vue'

    export default {
        data(){
            return {
                resource: resources.INSURANCE
            }
        },
        components: {
            listComponent, buttonAdd, insuranceSearchBar
        },
        created() {


                var i
                // contracts/id/insurances
                // fetch Contracts
                this.fetchInsurances().then(insurances => {
                    this.setFilteredInsurances(insurances)
                })


        },
        computed: {
            ...mapGetters([
                'client',
                'insurances',
                'filteredInsurances',
                'getInsurancesByAll',
                'getInsurancesByAllAdvanced',
                'activeFunction',
                'isAuthorizedForAllResources'
            ]),
            authorizedForAll(){
                return this.isAuthorizedForAllResources(this.resource, actions.READ_ALL)
            },
        },
        methods: {
            ...mapActions([
                'fetchInsurances',
                'deleteInsurance',
                'fetchClient',
                'fetchInsuranceByContract',
                'fetchInsurancesByCompany'
            ]),

            ...mapMutations([
                'setFilteredInsurances'
            ]),
            updateInsurance(value){
                console.log(value)
                if(value!==''){
                    this.setFilteredInsurances(this.getInsurancesByAll(value))
                }
                else {
                    this.setFilteredInsurances(this.insurances)
                }
            },
            updateInsurancesAdvanced(filterInsurance){
                this.setFilteredInsurances(this.getInsurancesByAllAdvanced(filterInsurance))
            }
        }
    }
</script>
