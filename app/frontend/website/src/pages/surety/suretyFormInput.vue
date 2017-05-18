<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
        <checkbox-input-form-group
                :object="object" name="flat" :text="$t('surety.flat')" >
        </checkbox-input-form-group>
      <!--surety type-->
        <select-input-form-group
                :object="object" name="suretyType" optionPropertyName="name" visibleKey="translation"
                :text="$t('surety.surety')" :rules="'required'" :options="suretyTypes">
        </select-input-form-group>
      <!-- premium -->

       <euro-input-form-group
          :object="object" name="premium"
          :rules="'required'"
          :text="object.flat==true ? $t('surety.premium'): $t('surety.minPremium')" >
      </euro-input-form-group>

      <!-- premium percentage -->

       <percent-input-form-group v-if="object.flat==false||object.flat==undefined"
          :object="object" name="premiumPercentage" :text="$t('surety.premiumPercentage')" :rules="'required'">  
      </percent-input-form-group>

      <!-- Insurance Company -->
        <select-input-form-group 
                     :object="object" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                     :text="$t('clientTypes.insuranceCompany')" :rules="'required'" :options="clients">
        </select-input-form-group>

      <div class="page-header">
        <button-add :resource="resource"></button-add>
        <h2>{{$t("surety.coverage") | capitalize }} </h2>
      </div>

      <div class="page-header">

        <select-input-form-group 
                     :object="selectedCondition" name="id" optionPropertyName="id" visibleKey="referenceCode"
                     :text="$t('condition.condition')" :options="conditions" rules="">
        </select-input-form-group>

       <button @click='pushCondition()' type="button" class="btn pull-right btn btn-primary "> {{$t("common.add") | capitalize }} </button>
      </div>

    <!-- All special conditions of surety -->
    <list-component :resource="resource" :listObject="listObject">
    </list-component> 

  </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import percentInputFormGroup from '../../assets/form/FormGroups/PercentInputformGroup.vue'
    import EuroInputFormGroup from '../../assets/form/FormGroups/EuroInputFormGroup.vue'
    import CheckboxInputFormGroup from '../../assets/form/FormGroups/CheckBoxInputGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import listComponent from "../../assets/general/listComponent.vue"
    import suretyTypes from "../../constants/suretyTypes"

    export default {
        data(){
            return{
                referenceCode: 'referenceCode',
                selectedCondition: {},
                id:'id',
                suretyTypes:suretyTypes,
                resource: resources.CONDITION,
                flatData: [{text:'true', value: true},{text:"false" , value: false}]
            }
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            percentInputFormGroup,CheckboxInputFormGroup,SelectInputFormGroup,DateInputFormGroup,listComponent,buttonAdd,EuroInputFormGroup
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        computed: {
            ...mapGetters([
                'clients',  
                'fleets',
                'suretyData',
                'suretyDetail',
                'contractId',
                'sureties',
                'vehicles',
                'conditions',
                'insuranceCompanyId',
                'selectedConditions'
                ]),
             listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','title','text'];
                listObj.values = this.selectedConditions;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchConditions',
                'fetchCondition',
                'fetchClientsBy',
                ]),
            ...mapMutations([
                'setConditions',
                'setCondition',
                'setSelectedConditions',
                'addSelectedCondition',
                'clearSelectedConditions'
                ]),
            pushCondition(){
                // fetch info for selected special condition
                this.fetchCondition({id: this.selectedCondition.id}).then(condition => {
                    // add selected condition to list
                    this.addSelectedCondition(condition)
                })
            },
        },
        created(){
          // set correct insurance company id 
          this.object.insuranceCompany = this.insuranceCompanyId
          // clear previous storage for selected conditions
          this.clearSelectedConditions()
          // Fetch all special conditions
          this.fetchConditions()
          // get all insurance companies
          this.fetchClientsBy({filters: {type: clientTypes.INSURANCE_COMPANY.type}}).then(() => {
          })
          // bind specialConditions to seleced conditions
          this.object.specialConditions = this.selectedConditions
        }
    }
</script>