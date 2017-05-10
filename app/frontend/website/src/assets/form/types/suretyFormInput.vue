<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--surety type select-->
       <form-input :placeholder="$t('surety.surety') | capitalize" :label="$t('surety.surety') | capitalize"
      v-model="surety.suretyType"></form-input>
       <form-input :placeholder="$t('surety.premium') | capitalize" :label="$t('surety.premium') | capitalize"
      v-model="surety.premium"></form-input>
       <form-input :placeholder="$t('surety.premiumPercentage') | capitalize" :label="$t('surety.premiumPercentage') | capitalize"
      v-model="surety.premiumPercentage"></form-input>
      <form-input :placeholder="$t('surety.flat') | capitalize" :label="$t('surety.flat') | capitalize"
      v-model="surety.flat"></form-input>

      <div class="page-header">
        <button-add :resource="resource"></button-add>
        <h2>{{$t("surety.coverage") | capitalize }} </h2>

      </div>

      <div class="page-header">
       <form-select v-model="this.selectedCondition" :options='conditions' :optionKey="this.referenceCode" :optionProperty='this.id' :label="$t('condition.condition') | capitalize" ></form-select>
       <button @click='pushCondition()' type="button" class="btn btn-primary">Voeg toe</button>
       </div>

      <list-component :resource="resource" :listObject="listObject">
    </list-component>

  </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import formInput from '../FormGroups/TextInputFormGroup.vue'
    import formDateInput from '../FormGroups/DateInputFormGroup.vue'
    import formSelect from '../FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../../constants/clientTypes'
    import listComponent from "../../list/listComponent.vue"
    import resources from '../../../constants/resources'
    import buttonAdd from '../../buttons/buttonAdd.vue'

    export default {
        data(){
            return{
                referenceCode: 'referenceCode',
                selectedCondition: '',
                id:'id',
                resource: resources.CONDITION,
            }
        },
        props: {
            surety: Object,
        },
        components: {
            formInput,formSelect,formDateInput,listComponent,buttonAdd
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
                'selectedConditions'
                ]),
             listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','Title'];
                listObj.values = this.selectedConditions;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchConditions',
                'fetchCondition'
                ]),
            ...mapMutations([
                'setConditions',
                'setCondition',
                'addSelectedCondition',
                'setSelectedConditions'
                ]),
            pushCondition(){
                // fetch condition with selected id
                this.fetchCondition({id:this.id})
                // push condition to list of selected conditions
                this.addSelectedCondition(this.condition)
            }
        },
        created(){
          // Fetch all special conditions
           this.fetchConditions()
          // display special conditions of surety (EDIT)
          this.setSelectedConditions(this.surety.specialConditions)
          // bind selected conditions to special conditions of surety
          this.surety.specialConditions = this.selectedConditions
        }
    }
</script>