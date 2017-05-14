<!--
    This page is used to edit a certain insurance.

    @param id: the id of the fleet which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="contract" :back="back" :resource="resource">
        <contract-form-input :object="contract" :actions="actions"></contract-form-input>
    </abstract-form>
</template>

<script>
    import {mapActions,mapMutations} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import contractFormInput from './contractFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.CONTRACT,
                contract:{},
                back:{name:resources.CONTRACT.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchContract({id: this.id}).then(contract => {
                    this.contract = contract;$
                    // Enable dates to fill in datepicker
                    this.contract.startDate = this.contract.startDate.substring(0,10)
                    this.contract.endDate = this.contract.endDate.substring(0,10)
                    // set Insurance company id
                    this.setInsuranceCompanyId(this.contract.insuranceCompany)                    
                })
                // set contractId
                this.setContractId(this.id)
            }
        },
        components: {
            abstractForm,contractFormInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchContract'
            ]),
            ...mapMutations([
                'setContractId',
                'setInsuranceCompanyId'
            ]),

        }
    }
</script>

