<!--
    This page shows all insurances from a contract
    @param id: The id of the contract for which the insurances are going to be shown.

-->
<template>
    <div>
        <div class="page-header" v-if="contract">
            <h1 >
                {{$t("contract.contract") | capitalize }} {{contract.customerName}} - {{contract.insuranceCompanyName}}
                <delete-component  :resource="resourceContract" :id="contract.id" ></delete-component>
            </h1>
            <h4 >{{contract.startDate.showableDate()}} - {{contract.endDate.showableDate()}}</h4>
            <h4> {{$t('contract.totalCostEuro') | capitalize }}: {{contract.totalCostEuro}}</h4>
            <h4>{{$t('contract.totalTaxEuro') | capitalize }}: {{contract.totalTaxEuro}}</h4>
        </div>

        <div class="page-header">
            <h1>
                {{$t("vehicle_insurance.vehicle_insurances") | capitalize }}
                <button-add :resource="resourceInsurance" :params="{contractId:id}"></button-add>
            </h1>
        </div>
        
        <table class="table-hover table">
            <thead>
            <tr>
                <th v-for="head in listObject.headers">
                    {{$t(resourceInsurance.name + '.' + head).capitalize()}}
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="value in listObject.values" class="list-tr">
                <td v-for="header in listObject.headers" class="clickable-td" @click="tdclick(value)">
                    {{value[header]}}
                </td>
                <td class="stretch">
                    <button-edit :resource="resourceInsurance" :params="{contractId:value.contract ,id:value.id}" ></button-edit>
                    <button-remove :resource="resourceInsurance"  @click="tdshowModal(value.id)"></button-remove>
                </td>
            </tr>
            </tbody>
        </table>
        <!-- Confirmation Modam -->
        <confirm-modal v-show="showModal"
                       @cancelModal="cancelCorrection()"
                       @confirmModal="SubmitFormHandler.submit()"
                       @close="showModal=false"
                       :object="correction"
                       :endDate="$t('insurance.endDate') | capitalize"
                       :modalHeaderTitle=" $t('modal.titleCorrection') | capitalize"
                       :modalBodyText="$t('modal.textCorrection') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize ">
        </confirm-modal>
        <button-back :route="{name: 'contracts'}"></button-back>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import {translateSuretyTypes,centsToEuroObject,centsToEuroArray} from '../../utils/utils'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import confirmModal from '../../assets/general/modal.vue'
    import {SubmitFormHandler} from '../../assets/form/SubmitFormHandler'
    import deleteComponent from '../../assets/general/deleteComponent.vue'

    export default {
        data(){
            return {
                resourceInsurance: resources.INSURANCE,
                resourceContract:resources.CONTRACT,
                ids:{contract:this.id},
                correction: {},
                SubmitFormHandler: SubmitFormHandler,
                showModal: false
            }
        },
        components: {
            buttonBack,buttonAdd,listComponent,buttonLink,
            buttonEdit,buttonRemove,confirmModal,deleteComponent
        },
        props: {
            id: String
        },
        created(){
            this.$on('mounted', components => this.initializeFormHandler(components));
            this.setLoading({loading: true })
            // fetch contract to display information
            let contractId = this.id;
            this.fetchContract({id: contractId}).then(()=>{
                centsToEuroObject(this.contract,"totalCost")
                centsToEuroObject(this.contract,"totalTax")
            });

            // get all insurances from the contract with contract Id
            this.fetchInsurances({ids:{contract: this.id}}).then(() => {
                translateSuretyTypes(this.insurances);
                centsToEuroArray(this.insurances,"cost");
                centsToEuroArray(this.insurances,"tax");
                centsToEuroArray(this.insurances,"insuredValue");
                this.setLoading({loading: false });
                this.show=true;
            });
         SubmitFormHandler.setSubmitFunction(this.confirmCorrection)

        },
        computed: {
            ...mapGetters([
                'contract',
                'insurance',
                'sureties',
                'insurances',
                'contractInsurances',
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['licensePlate','brand','suretyTypeTranslation','insuredValueEuro','showableStartDate','costEuro','taxEuro'];
                listObj.values = this.contractInsurances;
                return listObj;
            },
            contractInsurances(){
                var contractInsurances = []
                for(let i=0;i<this.insurances.length;i++){
                    if(this.insurances[i].contract == this.contract.id){
                        contractInsurances.push(this.insurances[i])
                    }
                }
                return contractInsurances
            }
        },
        methods: {
            ...mapActions([
                'fetchInsurancesByContract',
                'fetchInsurances',
                'fetchContract',
                'fetchInsurances',
                'fetchSureties',
                'fetchInsurance',
                'deleteBodyInsurance'
            ]),
            ...mapMutations([
                'setLoading',
            ]),
            tdclick: function(value) {
                this.$router.push({name: this.resourceInsurance.name, params: {contractId:value.contract, id:value.id}});
            },
            confirmCorrection: function(){
                if(this.correction.endDate) {
                    this.showModal = false
                    this.correction.endDate =  this.correction.endDate;
                    this.deleteBodyInsurance({id: this.selectedvalue, ids: this.ids, data: this.correction.endDate})
                }else{

                }
            },
            cancelCorrection : function(){
                this.showModal = false
            },
            tdshowModal: function(id) {
                this.showModal = true;
                this.selectedvalue=id;
            },
            initializeFormHandler(components){
                SubmitFormHandler.setInputComponents(components)
                SubmitFormHandler.setSubmitFunction(this.confirmCorrection)
            }
        },
    }
</script>