<!--
    This page is used to create a new insurance.
-->
<template>
    <correction-form :actions="actions" :object="insurance" :ids="ids" :back="back" :resource="resource">
        <insurance-form-input :contractId="contractId"  :object="insurance" :actions="actions"></insurance-form-input>
    </correction-form>
</template>

<script>
    import correctionForm from '../../assets/form/FormParts/CorrectionFormPart.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import insuranceFormInput from './insuranceFormInput.vue'
    import {mapGetters, mapActions,mapMutations} from 'vuex'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.INSURANCE,
                back: {name: resources.CONTRACT.name,params:{id:this.contractId}},
                ids:{contract:this.contractId},
                insurance: {contract:this.contractId}
            }
        },
        props:{
            contractId:String
        },
        components: {
            insuranceFormInput,correctionForm
        }
    }
</script>