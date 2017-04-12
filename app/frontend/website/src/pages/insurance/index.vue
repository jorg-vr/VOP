<!--
    This page shows all insurances in the database.
    From this page a new insurance can be added or an existing insurance can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>{{$t("insurance.insurances") | capitalize }}
            <button-link :route="{name: 'new_insurance'}" buttonClass="pull-right btn btn-md btn-primary btn-add"> 
            {{$t("common.add") | capitalize }} {{$t("insurance.insurance")}}
            </button-link>
            </h1>
        </div>
        <insurance-search-bar @search="updateInsurance" @advancedSearch="updateInsurancesAdvanced"></insurance-search-bar>
        <!-- Render an info-pane for every insurance. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="insurance in filteredInsurances"
                        v-if="insurance"
                        :object="insurance"
                        :visibleKeys="new Array('type','vehicle')"
                        :remove="deleteInsurance"
                        edit="edit_insurance"
                        show="insurance"
                        :key="insurance.id">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import insuranceSearchBar from '../../assets/search/types/insuranceSearchBar.vue'

    export default {
        components: {
            listComponent, buttonLink, insuranceSearchBar
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
