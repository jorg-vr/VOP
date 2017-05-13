<!--
    This page is used to create a new insurance.
-->
<template>
    <abstract-form :actions="actions" :object="insurance" :back="back" :resource="resource">
        <insurance-form-input :object="insurance" :actions="actions"></insurance-form-input>
    </abstract-form>
</template>

<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
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
                back: {name: resources.INSURANCE.name.plural()}

            }
        },
        props:{
            contractId:String
        },
        components: {
            insuranceFormInput,abstractForm
        },
        computed: {
            ...mapGetters([
                'contractId'
                ])
        },
    }
</script>