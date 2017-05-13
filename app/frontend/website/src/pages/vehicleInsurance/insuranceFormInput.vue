<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
      <!--default information vehicle insurance -->

       <!-- Start Date -->
        <date-input-form-group 
                    :object="object" name="startDate" :text="$t('insurance.startDate')" :rules="'required'" visibleKey="startDate">
        </date-input-form-group>

        <!-- End Date --> 
        <date-input-form-group 
                    :object="object" name="endDate" rules="" :text="$t('insurance.endDate')" visibleKey="endDate">
        </date-input-form-group>

        <!-- Insured value -->
        <text-input-form-group :object="object" name="insuredValue" :text="$t('insurance.insuredValue')" :rules="'required|numeric'"></text-input-form-group>

        <!-- Franchise -->
        <text-input-form-group :object="object" name="franchise" :text="$t('insurance.franchise')" :rules="'required|numeric'"></text-input-form-group>

      <!-- insurance surety for which vehicle is insured -->
      <div class="page-header">
       <button-add :resource="resource"></button-add>
        <h2>{{$t("insurance.type") | capitalize }} </h2>
      </div>

      <select-input-form-group  v-if="show"
                     :object="object" name="surety" optionPropertyName="id" visibleKey="suretyTypeTranslation"
                     :text="$t('surety.surety')" :rules="'required'" :options="sureties">
      </select-input-form-group>

      <!-- Insured vehicle -->
      <div class="page-header">
        <h2>{{$t("insurance.vehicle") | capitalize }}</h2>
      </div>
    
      <select-input-form-group 
                     :object="object" name="vehicle" optionPropertyName="id" visibleKey="licensePlate"
                     :text="$t('vehicle.vehicle')" :rules="'required'" :options="vehicles">
      </select-input-form-group>
    
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
    import {translateSuretyTypes} from '../../utils/utils'

    export default {
        data(){
            return{
                licensePlate: 'licensePlate',
                suretyType: 'suretyType',
                id:'id',
                resource: resources.SURETY,
                show:false
            }
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        props: {
            object: Object,
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup,DateInputFormGroup,buttonAdd
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'suretyData',
                'suretyDetail',
                'contract',
                'sureties',
                'vehicles',
                'insuranceCompanyId'
                ]),

        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehiclesBy',
                'fetchContract'
                ])
        },
        created(){
            this.fetchContract({id:this.object.contract}).then(()=>{
                // fetch all possible sureties
                this.fetchSureties({ids:{ company:this.contract.insuranceCompany}}).then(()=>{
                    translateSuretyTypes(this.sureties);
                    this.show=true;
                });
                // fetch all vehicles
                this.fetchVehiclesBy({filters:{company:this.contract.customer}})
            })

        }
    }
</script>