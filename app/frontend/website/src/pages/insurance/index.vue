<!--
    This page shows all insurances in the database.
    From this page a new insurance can be added or an existing insurance can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("insurance.insurance") | capitalize}}
               <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <insurance-search-bar @search="updateInsurance" @advancedSearch="updateInsurancesAdvanced"></insurance-search-bar>
        <!-- Render an info-pane for every insurance. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="insurance in filteredInsurances"
                        v-if="insurance"
                        :resource="resource"
                        :object="insurance"
                        :visibleKeys="new Array('type','vehicle')"
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
             this.fetchInsurances().then(insurances => {
                 this.setFilteredInsurances({insurances: insurances})
            })
        },
        computed: {
            ...mapGetters([
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
