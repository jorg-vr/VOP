<!--
    This page is used to edit a certain insurance.

    @param id: the id of the fleet which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="insurance" :back="back" :resource="resource">
        <insurance-form-input :object="insurance"></insurance-form-input>
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
                insurance:{},
                back:{name:resources.INSURANCE.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchInsurance({id: this.id,ids: this.contractId}).then(insurance => {
                    this.insurance = insurance;
                })
            }
        },
        components: {
            abstractForm,insuranceFormInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchInsurance'
            ]),
        computed: {
            ...mapGetters([
                'contractId'
                ])
        },

        }
    }
</script>

