<!--
    This page is used to create a new insurance.
-->
<template>
    <correction-form :actions="actions" :object="insurance" :back="back" :resource="resource">
        <insurance-form-input :object="insurance" :actions="actions"></insurance-form-input>
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
                insurance: {contract:this.contractId},
                back: {name: resources.INSURANCE.name,params:{id:this.contractId}}

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