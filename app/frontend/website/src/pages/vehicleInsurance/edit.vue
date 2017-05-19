<!--
    This page is used to edit a certain insurance.

    @param id: the id of the fleet which is edited.
-->
<template>
    <correction-form :actions="actions" :object="insurance" :back="back" :resource="resource" :ids="{contract: insurance.contract}">
        <insurance-form-input :contractId="contractId" :object="insurance"></insurance-form-input>
    </correction-form>
</template>

<script>
    import {mapActions,mapGetters} from 'vuex'
    import correctionForm from '../../assets/form/FormParts/CorrectionFormPart.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import insuranceFormInput from './insuranceFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.INSURANCE,
                insurance: {contract:this.contractId},
                back: {name:resources.INSURANCE.name,params:{id:this.contractId}}

            }
        },
        created(){
            this.fetchInsurance({id: this.id, ids:{contract: this.contractId}}).then(insurance => {
                this.insurance = insurance;
            })
        },
        components: {
            correctionForm,insuranceFormInput
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