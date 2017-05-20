<!--
    This page is used to edit a certain insurance.

    @param id: the id of the fleet which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="insurance" :back="back" :resource="resource" :ids="{contract: insurance.contract}">
        <insurance-form-input :contractId="contractId" :object="insurance"></insurance-form-input>
    </abstract-form>
</template>

<script>
    import {mapActions,mapGetters} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import insuranceFormInput from './insuranceFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.INSURANCE,
                insurance: {contract:this.contractId},
                back: {name:resources.INSURANCE.name,params:{contractId:this.contractId}}

            }
        },
        created(){
            this.fetchInsurance({id: this.id, ids:{contract: this.contractId}}).then(insurance => {
                this.insurance = insurance;
            })
        },
        components: {
            abstractForm,insuranceFormInput
        },
        props: {
            id: String,
            contractId:String
        },
        methods: {
            ...mapActions([
                'fetchInsurance'
            ])

        }
    }
</script>