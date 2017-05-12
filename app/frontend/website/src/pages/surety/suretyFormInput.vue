<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--surety type-->

      <text-input-form-group 
          :object="object" name="suretyType" :text="$t('surety.surety')" :rules="'required'">  
      </text-input-form-group>

      <!-- premium -->

       <text-input-form-group 
          :object="object" name="premium" :text="$t('surety.premium')" :rules="'required'">  
      </text-input-form-group>

      <!-- premium percentage -->

       <text-input-form-group 
          :object="object" name="premiumPercentage" :text="$t('surety.premiumPercentage')" :rules="'required'">  
      </text-input-form-group>

      <!-- flat -->
      <!-- change to select -->
 <!--       <text-input-form-group 
          :object="object" name="flat" :text="$t('surety.flat')" :rules="'required'">  
      </text-input-form-group> -->

      <!-- Flat -->
      <select-input-form-group 
                     :object="object" name="isFlat" optionPropertyName="value" visibleKey="text"
                     :text="$t('surety.flat')" :rules="'required'" :options="flatData">
        </select-input-form-group>


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
                     :object="object" name="selectedCondition" optionPropertyName="id" visibleKey="referenceCode"
                     :text="$t('condition.condition')" :rules="'required'" :options="conditions">
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
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import listComponent from "../../assets/list/listComponent.vue"

    export default {
        data(){
            return{
                referenceCode: 'referenceCode',
                selectedCondition: '',
                id:'id',
                resource: resources.CONDITION,
                flatData: [{text:'true', value: true},{text:"false" , value: false}]
            }
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup,DateInputFormGroup,listComponent,buttonAdd
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
                this.fetchCondition({id: this.object.selectedCondition}).then(condition => {
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