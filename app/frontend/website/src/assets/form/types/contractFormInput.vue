<!--
All of the fields for contract input for the contract form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
       <form-select v-model="contract.customer":options='clients' :optionKey="this.name" :optionProperty='this.id' :label="$t('contract.customer') | capitalize" ></form-select>

       <form-select v-model="contract.insuranceCompany":options='clients' :optionKey="this.name" :optionProperty='this.id' :label="$t('contract.insuranceCompany') | capitalize" ></form-select>


      <form-date-input :placeholder="$t('contract.showableStartDate') | capitalize" :label="$t('contract.showableStartDate') | capitalize"
      v-model="contract.startDate"></form-date-input>
      <form-date-input :placeholder="$t('contract.showableEndDate') | capitalize" :label="$t('contract.showableEndDate') | capitalize"
      v-model="contract.endDate"></form-date-input>

      <div v-if="show">
            <div class="page-header">
                <button-add :resource="resource1"></button-add>
                <h2>{{$t("vehicle_insurance.vehicle_insurances") | capitalize }} </h2>
            </div>

        <list-component :resource="resource1" :listObject="listObject1">
        </list-component>
            
            <div class="page-header">
                <h2>
                     <button-add :resource="resource2"></button-add>
                    {{$t("surety.sureties") | capitalize }}
                </h2>
            </div>

        <list-component :resource="resource2" :listObject="listObject2">
        </list-component>
            <list-component :resource="resource2"
                        :objects="sureties"
                        :visibleKeys="['suretyType','premium']">
            </list-component>

        </div>



  </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import formInput from '../FormGroups/TextInputFormGroup.vue'
    import formDateInput from '../FormGroups/DateInputFormGroup.vue'
    import formSelect from '../FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../../constants/clientTypes'
    import resources from '../../../constants/resources'
    import buttonAdd from '../../buttons/buttonAdd.vue'
    import listComponent from "../../list/listComponent.vue"

    export default {
        data(){
            return{
                id:'id',
                name:'name',
                show: false,
                resource1: resources.INSURANCE,
                resource2: resources.SURETY
            }
        },
        props: {
            actions: Object,
            contract: Object,
        },
        components: {
            formInput,formSelect,formDateInput,buttonAdd,listComponent
        },
        computed: {
            ...mapGetters([
                'insurances',
                'clients',
                'fleets',
                'suretyData',
                'suretyDetail',
                'contractId',
                'sureties',
                'vehicles',
                'filteredcontractInsurances'
                ]),
            listObject1() {
                var listObj = {};
                listObj.headers = ['cost','tax','showableStartDate'];
                listObj.values = this.insurances;
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['suretyType','premium'];
                listObj.values = this.sureties;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchClients',
                'fetchInsurances'
                ]),
            ...mapMutations([
                'setLoading'
            ]),
        },
        created(){
            // fetch all possible clients
            this.fetchClients()
            // EDIT
            if(this.actions.name == 'update'){
                // fetch all vehicle insurance for contract
                this.show = true
                this.setLoading({loading: true })
                // get all insurances from the contract with contract Id
                this.fetchInsurances({ids: this.contract.id}).then(() => {
                    this.setLoading({loading: false })
                })
                // get all sureties for the chose insuranceCompany
                this.fetchSureties()
            }

            
        }
    }
</script>