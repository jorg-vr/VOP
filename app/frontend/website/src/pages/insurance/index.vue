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
        <list-component v-for="insurance in filteredInsurances"
                        v-if="insurance"
                        :resource="resource"
                        :object="insurance"
                        :visibleKeys="['name','startDate','totalCost','totalTax']"
                        :key="insurance.id">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
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
                for(i=0; i<insurances.length;i++){
                   // for each contract add customer name for display purposes
                   console.log(insurances[i])
                   // insurances[i].customerName = this.client.name
                   // insuranceCompany not supported yet
                }  
                this.setFilteredInsurances(insurances)
            })
        },
        computed: {
            ...mapGetters([
                'client',
                'insurances',
                'filteredInsurances',
                'getInsurancesByAll',
                'getInsurancesByAllAdvanced'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInsurances',
                'deleteInsurance',
                'fetchClient',
                'fetchInsuranceByContract'
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
