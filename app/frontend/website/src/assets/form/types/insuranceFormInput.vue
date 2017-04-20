<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--surety type select-->
      <form-input :placeholder="$t('insurance.startdate') | capitalize" :label="$t('insurance.startdate') | capitalize"
      v-model="insurance.startdate"></form-input>
      <form-input :placeholder="$t('insurance.enddate') | capitalize" :label="$t('insurance.enddate') | capitalize"
      v-model="insurance.enddate"></form-input>
      <form-input :placeholder="$t('insurance.totalCost') | capitalize" :label="$t('insurance.totalCost') | capitalize"
      v-model="insurance.cost"></form-input>
      <form-input :placeholder="$t('insurance.tax') | capitalize" :label="$t('insurance.tax') | capitalize"
      v-model="insurance.tax"></form-input>
      <form-input :placeholder="$t('insurance.totalCost') | capitalize" :label="$t('insurance.totalCost') | capitalize"
      v-model="insurance.surety"></form-input>



      <h2>{{$t("insurance.type") | capitalize }} </h2>
      <form-input :placeholder="$t('insurance.type') | capitalize" :label="$t('insurance.type') | capitalize"
      v-model="insurance.surety.suretyType"></form-input>

      <form-input :placeholder="$t('insurance.percentage') | capitalize" :label="$t('insurance.percentage') | capitalize"
      v-model="insurance.surety.premiumPercentage"></form-input>

      <form-input :placeholder="$t('insurance.premium') | capitalize" :label="$t('insurance.premium') | capitalize"
      v-model="insurance.surety.premium"></form-input> 

      <div class="page-header">
        <h2>{{$t("insurance.vehicle") | capitalize }}</h2>
    </div>

<!--         <form-select v-model="insurance.company":options='clients' :optionKey="this.name" :optionProperty='this.id' :label="$t('client.company') | capitalize" ></form-select>
    <form-select v-if="insurance.company" v-model="insurance.company2" :options='clients' :optionKey="this.name" :optionProperty='this.id' :label="$t('client.company') | capitalize" ></form-select> -->
    <form-input :placeholder="$t('insurance.vehicle') | capitalize" :label="$t('insurance.vehicle') | capitalize" v-model="insurance.vehicle"></form-input>
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
                name: 'name',
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
                'suretyDetail'
                ])
        },
        methods: {
            ...mapActions([
                'fetchClients',
                'fetchFleets',
                'fetchSuretyTypes'
                
                ])
        },
        created(){
            // TODO 
            // SET CONTRACT OF SURETY
            // fetch all companies for select (customers)
            
            this.fetchClients()
            this.fetchSuretyTypes()
            console.log('fetch all necessary for select options: all companies, all insuranceCompany, all contract types')
            console.log(this.suretyData)
        }
    }
</script>