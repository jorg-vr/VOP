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
        <select-input-form-group v-if="this.actions.name === 'create'"
                     :object="object" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                     :text="$t('clientTypes.insuranceCompany')" :rules="'required'" :options="insuranceCompanies">
        </select-input-form-group>

        <!-- Start Date -->
        <date-input-form-group
                    :object="object" name="startDate" :text="$t('insurance.startDate')" :rules="'required'">
        </date-input-form-group>

        <!-- End Date -->
        <date-input-form-group
                    :object="object" name="endDate" :text="$t('insurance.endDate')" :rules="'required'">
        </date-input-form-group>

        <!-- Show if contractFormInput is used to edit -->
        <div v-if="this.actions.name === 'update'">
            <div class="page-header">
                <button-add :resource="resource1"></button-add>
                <h2>{{$t("vehicle_insurance.vehicle_insurances") | capitalize }} </h2>
            </div>

        <table class="table-hover table">
            <thead>
            <tr>
                <th v-for="head in listObject1.headers">
                    {{$t(resource1.name + '.' + head).capitalize()}}
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="value in listObject1.values" class="list-tr">
                <td v-for="header in listObject1.headers" class="clickable-td" @click="tdclick(value)">
                    {{value[header]}}
                </td>
                <td class="stretch">
                    <button-edit :resource="resource1" :params="{contractId:value.contract ,id:value.id}" ></button-edit>
                    <button-remove :resource="resource1"  @click="tdshowModal(value.id)"></button-remove>
                </td>
            </tr>
            </tbody>
        </table>

            <div class="page-header">
                <h2>
                     <button-add :resource="resource2"></button-add>
                    {{$t("surety.sureties") | capitalize }}
                </h2>
            </div>
        <h5> {{$t("contract.offer") | capitalize }} {{object.insuranceCompanyName}} </h5>
        <list-component v-if="show2" :resource="resource2" :listObject="listObject2">
        </list-component>

        </div>

        <!-- Confirmation Modam -->
        <confirm-modal v-show="showModal"
                       @cancelModal="cancelCorrection"
                       @confirmModal="confirmCorrection()"
                       @optional="showModal=false"
                       @close="showModal=false"
                       :object="insurance"
                       :endDate="$t('insurance.endDate') | capitalize"
                       :modalHeaderTitle=" $t('modal.titleCorrection') | capitalize"
                       :modalBodyText="$t('modal.textCorrection') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize "
                       :optionalButtonText="$t('modal.cancel') | capitalize ">
        </confirm-modal>

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
    import listComponent from "../../assets/general/listComponent.vue"
    import {translateSuretyTypes} from '../../utils/utils'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import confirmModal from '../../assets/general/modal.vue'

    export default {
        data(){
            return{
                id:'id',
                name:'name',
                customer: 'customer',
                show1: false,
                show2: false,
                resource1: resources.INSURANCE,
                resource2: resources.SURETY,
                customers:[],
                insuranceCompanies:[],
                showModal: false
            }
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup,DateInputFormGroup,buttonAdd,listComponent,buttonRemove,buttonEdit,confirmModal
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
                'filteredcontractInsurances',
                'insuranceCompanyId',
                'insurance'
                ]),
            listObject1() {
                var listObj = {};
                listObj.headers = ['licensePlate','brand','suretyTypeTranslation','insuredValue','showableStartDate','cost','tax'];
                listObj.values = translateSuretyTypes(this.contractInsurances);
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['suretyTypeTranslation','premium'];
                listObj.values = translateSuretyTypes(this.sureties);
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
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchClients',
                'fetchInsurances',
                'fetchClientsBy',
                'fetchContract',
                'fetchInsurance',
                'createCorrection'
                ]),
            ...mapMutations([
                'setLoading'
            ]),
            tdclick: function(value) {
                this.$router.push({name: this.resource1.name, params: {contractId:value.contract, id:value.id}});
            },
            confirmCorrection: function(){
                // hide modal
                let correction = {}
                this.showModal=false
                // create correction object
                correction.vehicle= this.insurance.vehicle
                correction.contract = this.insurance.contract
                correction.date = this.insurance.endDate + "T00:00:00.00"
                correction.tax = this.insurance.tax
                this.deleteObject()
                this.createCorrection({companyId: this.object.customer, resource:correction})
            },
            cancelCorrection : function(){
                this.showModal = false
                this.deleteObject()
            },
            deleteObject : function(){
                this.$store.dispatch('delete' + this.resource1.name.capitalize(), {id: this.selectedvalue, ids: this.ids})
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
                // fetch clicked insuranc
                this.fetchInsurance({ids:{ contract:this.id}, id:this.selectedvalue}).then(insurance => {
                 })
            }
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
                this.fetchInsurances({ids:{contract: this.contractId}}).then(() => {
                    this.setLoading({loading: false })
                    this.show1=true
                })
                // get all sureties for the chose insuranceCompany
                // ERROR FIX
                this.fetchContract({id: this.contractId}).then(contract => {
                    this.fetchSureties({ids:{company: contract.insuranceCompany}})
                    this.show2=true
                })
                
            }


        }
    }
</script>