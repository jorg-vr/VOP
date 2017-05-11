<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--default information vehicle insurance -->
      <form-date-input :placeholder="$t('insurance.showableStartDate') | capitalize" :label="$t('insurance.showableStartDate') | capitalize"
      v-model="insurance.startDate"></form-date-input>
      <form-date-input :placeholder="$t('insurance.showableEndDate') | capitalize" :label="$t('insurance.showableEndDate') | capitalize"
      v-model="insurance.endDate"></form-date-input>
      <form-input :placeholder="$t('insurance.insurancedAmount') | capitalize" :label="$t('insurance.insurancedAmount') | capitalize"
      v-model="insurance.insuredValue"></form-input>
       <form-input :placeholder="$t('insurance.franchise') | capitalize" :label="$t('insurance.franchise') | capitalize"
      v-model="insurance.franchise"></form-input>

      <!-- insurance surety for which vehicle is insured -->
      <div class="page-header">
       <button-add :resource="resource"></button-add>
        <h2>{{$t("insurance.type") | capitalize }} </h2>
       
      </div>
      <form-select v-model="insurance.surety":options='sureties' :optionKey="this.suretyType" :optionProperty='this.id' :label="$t('insurance.type') | capitalize" ></form-select>


      <div class="page-header">
        <h2>{{$t("insurance.vehicle") | capitalize }}</h2>
      </div>
    
        <form-select v-model="insurance.vehicle":options='vehicles' :optionKey="this.licensePlate" :optionProperty='this.id' :label="$t('vehicle.vehicle') | capitalize" ></form-select>
    
  </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../../constants/resources'
        import formInput from '../FormGroups/TextInputFormGroup.vue'
    import formDateInput from '../FormGroups/DateInputFormGroup.vue'
    import formSelect from '../FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../../constants/clientTypes'
    import buttonAdd from '../../buttons/buttonAdd.vue'

    export default {
        data(){
            return{
                licensePlate: 'licensePlate',
                suretyType: 'suretyType',
                id:'id',
                resource: resources.SURETY,
            }
        },
        props: {
            insurance: Object,
        },
        components: {
            formInput,formSelect,formDateInput,buttonAdd
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'suretyData',
                'suretyDetail',
                'contractId',
                'sureties',
                'vehicles'
                ])
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles'
                
                ])
        },
        created(){
            // set contract id of insurance
            this.insurance.contract = this.contractId
            
            // fetch all possible sureties
            this.fetchSureties()
            // fetch all vehicles
            this.fetchVehicles()
        }
    }
</script>