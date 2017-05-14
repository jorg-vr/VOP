<!--
    This page shows all insurances from a contract
    @param id: The id of the contract for which the insurances are going to be shown.

-->
<template>
    <div>
        <div class="page-header" v-if="contract">
            <h1 >
                {{$t("contract.contract") | capitalize }} {{contract.customerName}} - {{contract.insuranceCompanyName}}
            </h1>
            <h4 >{{showDate(contract.startDate)}} - {{showDate(contract.endDate)}}</h4>
            <h4> {{$t('contract.totalCost') | capitalize }} {{contract.totalCost}}€</h4>
            <h4>{{$t('contract.totalTax') | capitalize }} {{contract.totalTax}}€</h4>
        </div>
        <!-- show information about insurance contract -->
        <!--<div class="col-md-8">-->
            <!--<table class="table show-table" v-if='contract'>-->
                <!--<tr>-->
                    <!--<td>{{$t('contract.customer') | capitalize }}</td>-->
                    <!--<td>{{contract.customerName}}</td>-->
                <!--</tr>-->
                <!--<tr>-->
                    <!--<td>{{$t('contract.insuranceCompany') | capitalize }}</td>-->
                    <!--<td>{{contract.insuranceCompanyName}}</td>-->
                <!--</tr>-->
                <!--<tr>-->
                    <!--<td>{{$t('contract.showableStartDate') | capitalize }}</td>-->
                    <!--<td>{{showDate(contract.startDate)}}</td>-->
                <!--</tr>-->
                <!--<tr>-->
                    <!--<td>{{$t('contract.showableEndDate') | capitalize }}</td>-->
                    <!--<td>{{showDate(contract.endDate)}}</td>-->
                <!--</tr>-->

                 <!--<tr>-->
                    <!--<td>{{$t('contract.totalCost') | capitalize }}</td>-->
                    <!--<td>{{contract.totalCost}}</td>-->
                <!--</tr>-->

                 <!--<tr>-->
                    <!--<td>{{$t('contract.totalTax') | capitalize }}</td>-->
                    <!--<td>{{contract.totalTax}}</td>-->
                <!--</tr>-->

            <!--</table>-->
        <!--</div>-->


        <div class="page-header">
            <h1>
                {{$t("vehicle_insurance.vehicle_insurances") | capitalize }}
                <button-add :resource="resource1" :params="{contractId:id}"></button-add>
            </h1>
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
        <!-- Confirmation Modam -->
        <confirm-modal v-show="showModal"
                       @cancelModal="showModal=false"
                       @confirmModal="confirmAction()"
                       :modalHeaderTitle=" $t('modal.titleConfirm') | capitalize"
                       :modalBodyText="$t('modal.textConfirm') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize ">
        </confirm-modal>

        <div class="page-header">
            <h1>
                <button-add :resource="resource2"></button-add>
                {{$t("surety.sureties") | capitalize }}
            </h1>
        </div>
        
        <h5 v-if="contract!=null"> {{$t("contract.offer") | capitalize }} {{contract.insuranceCompanyName}} </h5>
        <list-component v-if="show2" :resource="resource2" :listObject="listObject2">
        </list-component>

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
    import {translateSuretyTypes} from '../../utils/utils'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import confirmModal from '../../assets/general/modal.vue'

    export default {
        data(){
            return {
                resource1: resources.INSURANCE,
                resource2: resources.SURETY,
                show1: false,
                show2: false,
                ids:{contract:this.id},
                showModal:false
            }
        },
        components: {
            buttonBack,buttonAdd,listComponent,buttonLink,
            buttonEdit,buttonRemove,confirmModal
        },
        props: {
            id: String
        },
        created(){
            this.setLoading({loading: true })
            // fetch contract to display information
            let contractId = this.id;
            this.fetchContract({id: contractId}).then(()=>{
                // get all possible sureties for the chosen insurance Company of the contract
                this.fetchSureties({ids:{company:this.contract.insuranceCompany}}).then(() => {
                    this.sureties=translateSuretyTypes(this.sureties);
                    this.show2=true;
                })
            });

            // get all insurances from the contract with contract Id
            this.fetchInsurances({ids:{contract: this.id}}).then(() => {
                this.insurances=translateSuretyTypes(this.insurances);
                this.setLoading({loading: false });
                this.show1=true;
            });


            // set contract Id
            this.setContractId(contractId)
        },
        computed: {
            ...mapGetters([
                'contract',
                'insurance',
                'sureties',
                'insurances',
                'contractInsurances',
                'contractId',
            ]),
            listObject1() {
                var listObj = {};
                listObj.headers = ['licensePlate','brand','suretyTypeTranslation','insuredValue','showableStartDate','cost','tax'];
                listObj.values = this.contractInsurances;
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['suretyTypeTranslation','premium'];
                listObj.values = this.sureties;
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
                'fetchSureties'
            ]),
            ...mapMutations([
                'setContractId',
                'setLoading',
                'setInsuranceCompanyId'
            ]),
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            },
            tdclick: function(value) {
                this.$router.push({name: this.resource1.name, params: {contractId:value.contract, id:value.id}});
            },
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                // special case deletion of insurance
                this.$store.dispatch('delete' + this.resource1.name.capitalize(), {id: this.selectedvalue, ids: this.ids})
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            }
        },
    }
</script>