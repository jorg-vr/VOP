<!--
    This page shows all insurances from a contract
    @param id: The id of the contract for which the insurances are going to be shown.

-->
<template>
    <div>
        <div class="page-header">
            <h1>
                {{$t("contract.contract") | capitalize }}
            </h1>
        </div>
        <!-- show information about insurance contract -->
        <div class="col-md-8">
            <table class="table show-table" v-if='contract'>
                <tr>
                    <td>{{$t('contract.customer') | capitalize }}</td>
                    <td>{{contract.customerName}}</td>
                </tr>
                <tr>
                    <td>{{$t('contract.insuranceCompany') | capitalize }}</td>
                    <td>{{contract.insuranceCompanyName}}</td>
                </tr>
                <tr>
                    <td>{{$t('contract.showableStartDate') | capitalize }}</td>
                    <td>{{showDate(contract.startDate)}}</td>
                </tr>
                <tr>
                    <td>{{$t('contract.showableEndDate') | capitalize }}</td>
                    <td>{{showDate(contract.endDate)}}</td>
                </tr>

                 <tr>
                    <td>{{$t('contract.totalCost') | capitalize }}</td>
                    <td>{{contract.totalCost}}</td>
                </tr>

                 <tr>
                    <td>{{$t('contract.totalTax') | capitalize }}</td>
                    <td>{{contract.totalTax}}</td>
                </tr>

            </table>
        </div>


        <div class="page-header">
            <h1>
                {{$t("vehicle_insurance.vehicle_insurances") | capitalize }}
                <button-add :resource="resource1"></button-add>
            </h1>
        </div>
        

        <!-- TODO ADD MORE FIELDS -->
        <list-component :resource="resource1" :listObject="listObject1" :ids="{contract: this.id}">
        </list-component>

         <div class="page-header">
            <h1>
                <button-add :resource="resource2"></button-add>
                {{$t("surety.sureties") | capitalize }}
            </h1>
        </div>
        
        <h5 v-if="contract!=null"> {{$t("contract.offer") | capitalize }} {{contract.insuranceCompanyName}} </h5>
        <list-component :resource="resource2" :listObject="listObject2">
        </list-component>

        <button-back :route="{name: 'contracts'}"></button-back>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/list/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import insuranceSearchBar from '../../assets/search/types/insuranceSearchBar.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        data(){
            return {
                resource1: resources.INSURANCE,
                resource2: resources.SURETY,
                show: true
            }
        },
        components: {
            buttonBack,buttonAdd,listComponent,buttonLink,insuranceSearchBar
        },
        props: {
            id: String
        },
        created(){
            // fetch contract to display information
            let contractId = this.id
            this.fetchContract({id: contractId});

            this.setLoading({loading: true })
            // get all insurances from the contract with contract Id
            this.fetchInsurances({ids:{contract: this.id}}).then(() => {
                this.setLoading({loading: false })
            })
            // get all possible sureties for the chosen insurance Company of the contract
            this.setLoading({loading: true })
            this.fetchSureties().then(() => {
                this.setLoading({loading: false })
            })

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
                'filteredcontractInsurances',
                'contractId',
                'getContractInsurancesByAll',
                'getContractInsurancesByAllAdvanced'
            ]),
            listObject1() {
                var listObj = {};
                listObj.headers = ['licensePlate','brand','suretyType','insuredValue','showableStartDate','cost','tax'];
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
                var insuranceCompanies = []
                for(let i=0;i<this.sureties.length;i++){
                    if(this.sureties[i]!=null&&this.contract!=null&&this.sureties[i].insuranceCompany == this.contract.insuranceCompany){
                        insuranceCompanies.push(this.sureties[i])
                    }
                }
                return insuranceCompanies
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
                'setFilteredcontractInsurances',
                'setLoading',
                'setInsuranceCompanyId'
            ]),
            // for search
            updateContractInsurances(value){
                console.log(value)
                if(value!==''){
                    this.setFilteredcontractInsurances(this.getContractInsurancesByAll(value))
                }
                else {
                    this.setFilteredcontractInsurances(this.contractInsurances)
                }
            },
            updateContractInsurancesAdvanced(filterInsurance){
                this.setFilteredInsurances(this.getContractInsurancesByAllAdvanced(filterInsurance))
            },
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            }
        },
    }
</script>