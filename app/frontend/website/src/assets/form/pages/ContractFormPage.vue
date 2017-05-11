<!--
This page is used to edit or create a certain insurance.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <contract-form :actions="actions" :oldContract="oldContract"></contract-form>
    </div>
</template>
<script>
    import ContractForm from '../../../assets/form/types/ContractForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('contract', this.actions.name),
                oldContract: null
            }
        },
        components: {
            ContractForm
        },
         created(){
            // if EDIT
            if(this.id){
                this.fetchContract({id: this.id}).then(contract => {
                    this.oldContract = contract
                    // adjust dates to fit form date input
                    this.oldContract.startDate = this.oldContract.startDate.substring(0,10)
                    this.oldContract.endDate = this.oldContract.endDate.substring(0,10)
                    console.log(this.oldContract)
                })

                // Overbodig dmv veld in contract resource? (contract.insurances = [])
               /* this.fetchInsurances({ids:this.id}).then(insurances =>{
                    console.log(insurances)
                })*/
            }



           
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'suretyData',
                'suretyDetail',
                'insurance',
                'contractId',
                'contract'
            ])
        },
        methods: {
            ...mapActions([
                'fetchContract',
                'fetchSuretyDetail',
                'fetchInsurances'
            ]),
            ...mapMutations([
                'clearData',
                'clearInsurances'
                
            ]),
        }
    }
</script>