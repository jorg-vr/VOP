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
        <list-component :resource="resource" :objects="filteredInsurances" :visibleKeys="['showableStartDate','totalCost','totalTax']">
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
                resource: resources.INSURANCE
            }
        },
        components: {
            listComponent, buttonAdd, insuranceSearchBar
        },
        created() {
            this.setLoading({loading: true })
            if(this.authorizedForAll){
                this.fetchInsurances().then(() => {
                    this.setLoading({loading: false })
                })
            }
            else {
                this.fetchInsurancesBy({filters: {company: this.activeFunction.company}}).then(() => {
                    this.setLoading({loading: false })
                })
            }
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
                'fetchInsurancesBy',
                'fetchInsurances',
                'deleteInsurance',
                'fetchClient',
                'fetchInsuranceByContract',
                'fetchInsurancesByCompany'
            ]),

            ...mapMutations([
                'setFilteredInsurances',
                'setLoading'
            ]),
            updateInsurance(value){
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
