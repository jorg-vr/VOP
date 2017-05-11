<!--
All of the fields for contract input for the contract form

@param object: This object will be configured with the input of this component.
@param actions: Used to distinguish create from the edit action.
-->
<template>
    <div>
        <!-- Customer -->
         <select-input-form-group 
                     :object="object" name="customer" optionPropertyName="id" visibleKey="name"
                     :text="$t('clientTypes.customer')" :rules="'required'" :options="customers">
        </select-input-form-group>
        <!-- Insurance Company (not editable due to consistency reasons) -->
        <select-input-form-group v-if="this.actions.name == 'create'"
                     :object="object" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                     :text="$t('clientTypes.insuranceCompany')" :rules="'required'" :options="insuranceCompanies">
        </select-input-form-group>

        <!-- Start Date -->
        <date-input-form-group 
                    :object="object" name="startDate" :text="$t('insurance.startDate')" :rules="'required'" visibleKey="startDate">
        </date-input-form-group>

        <!-- End Date --> 
        <date-input-form-group 
                    :object="object" name="endDate" :text="$t('insurance.endDate')" :rules="'required'" visibleKey="endDate">
        </date-input-form-group>

        <!-- Show if contractFormInput is used to edit -->
        <div v-if="this.actions.name == 'update'">
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
        <h5> {{$t("contract.offer") | capitalize }} {{object.insuranceCompany}} </h5>
        <list-component :resource="resource2" :listObject="listObject2">
        </list-component>

        </div>



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
                id:'id',
                name:'name',
                customer: 'customer',
                show: false,
                resource1: resources.INSURANCE,
                resource2: resources.SURETY,
                customers:[],
                insuranceCompanies:[]
            }
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup,DateInputFormGroup,buttonAdd,listComponent
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
                listObj.values = this.contractInsurances;
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['suretyType','premium'];
                listObj.values = this.insuranceCompanySureties;
                return listObj;
            },
            insuranceCompanySureties(){
                var sureties = []
                for(let i=0;i<this.sureties.length;i++){
                    if(this.sureties[i].insuranceCompany == this.object.insuranceCompany){
                        sureties.push(this.sureties[i])
                    }
                }
                return sureties
            },
            contractInsurances(){
                var contractInsurances = []
                for(let i=0;i<this.insurances.length;i++){
                    if(this.insurances[i].contract == this.object.id){
                        contractInsurances.push(this.insurances[i])
                    }
                }
                return contractInsurances
            }
            /*customers(){
                var customers = []
                for(let i=0; i<this.clients.length;i++){
                    if(this.clients[i].type == "CUSTOMER"){
                        customers.push(this.clients[i])
                    }
                }
                return customers
            },
            insuranceCompanies(){
                var insuranceCompanies = []
                for(let i=0;i<this.clients.length;i++){
                    if(this.clients[i].type == "INSURANCE_COMPANY"){
                        insuranceCompanies.push(this.clients[i])
                    }
                }
                return insuranceCompanies
            }*/
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchClients',
                'fetchInsurances',
                'fetchClientsBy'
                ]),
            ...mapMutations([
                'setLoading'
            ]),
        },
        created(){
            // fetch all possible clients
            /*this.fetchClients().then(clients => {
                    console.log(clients)
                })*/
            // fetch all customers
            this.fetchClientsBy({filters: {type: clientTypes.CUSTOMER.type}}).then(clients => {
                this.customers = clients
            })
            // fetch all insurance companies
            this.fetchClientsBy({filters: {type: clientTypes.INSURANCE_COMPANY.type}}).then(clients => {
                this.insuranceCompanies = clients
            })


            // EDIT
            if(this.actions.name == 'update'){
                // fetch all vehicle insurance for contract
                this.show = true
                this.setLoading({loading: true })
                // get all insurances from the contract with contract Id
                this.fetchInsurances({ids: this.object.id}).then(() => {
                    this.setLoading({loading: false })
                })
                // get all sureties for the chose insuranceCompany
                this.fetchSureties()
            }

            
        }
    }
</script>