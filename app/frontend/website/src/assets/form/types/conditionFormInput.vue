<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
    <form-input :placeholder="$t('condition.Title') | capitalize" :label="$t('condition.Title') | capitalize"
                    v-model="condition.Title"></form-input>
    <form-input :placeholder="$t('condition.referenceCode') | capitalize" :label="$t('condition.referenceCode') | capitalize"
                    v-model="condition.referenceCode"></form-input>
    <form-input :placeholder="$t('condition.text') | capitalize" :label="$t('condition.text') | capitalize"
                    v-model="condition.text"></form-input>


  </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import formInput from '../FormGroups/TextInputFormGroup.vue'
    import formDateInput from '../FormGroups/DateInputFormGroup.vue'
    import formSelect from '../FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../../constants/clientTypes'
    import listComponent from "../../list/listComponent.vue"
    import resources from '../../../constants/resources'
    import buttonAdd from '../../buttons/buttonAdd.vue'

    export default {
        data(){
            return{
                referenceCode: 'referenceCode',
                selectedCondition: '',
                id:'id',
                resource: resources.CONDITION,
            }
        },
        props: {
            condition: Object,
        },
        components: {
            formInput,formSelect,formDateInput,listComponent,buttonAdd
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'suretyData',
                'suretyDetail',
                'contractId',
                'sureties',
                'vehicles',
                'conditions',
                'selectedConditions'
                ])
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchConditions'
                ]),
            ...mapMutations([
                'setConditions',
                'setCondition',
                'addSelectedCondition'
                ]),
        },

    }
</script>