<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--surety type select-->
      <form-input :placeholder="$t('insurance.startdate') | capitalize" :label="$t('insurance.startdate') | capitalize"
      v-model="insurance.startDate"></form-input>
      <form-input :placeholder="$t('insurance.enddate') | capitalize" :label="$t('insurance.enddate') | capitalize"
      v-model="insurance.endDate"></form-input>
      <form-input :placeholder="$t('insurance.insurancedAmount') | capitalize" :label="$t('insurance.insurancedAmount') | capitalize"
      v-model="insurance.insuredValue"></form-input>
       <form-input :placeholder="$t('insurance.franchise') | capitalize" :label="$t('insurance.franchise') | capitalize"
      v-model="insurance.franchise"></form-input>


      <div class="page-header">
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
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    import clientTypes from '../../../constants/clientTypes'

    export default {
        data(){
            return{
                licensePlate: 'licensePlate',
                suretyType: 'suretyType',
                id:'id',
            }
        },
        props: {
            insurance: Object,
            contractId: String
        },
        components: {
            formInput,formSelect
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
            // check results
            console.log('start check up')
            console.log('---------------')
            console.log(this.sureties)
            console.log(this.vehicles)
        }
    }
</script>