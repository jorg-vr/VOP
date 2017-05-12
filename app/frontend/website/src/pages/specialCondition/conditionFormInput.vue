<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
         <!-- Title -->
        <text-input-form-group 
                :object="object" name="title" :text="$t('condition.title')" :rules="'required'">
        </text-input-form-group>

        <!-- Code -->

        <text-input-form-group 
                :object="object" name="referenceCode" :text="$t('condition.referenceCode')" :rules="'required|numeric'">
        </text-input-form-group>

        <!-- Text -->

        <text-input-form-group 
                :object="object" name="text" :text="$t('condition.text')" :rules="'required'">
        </text-input-form-group>

  </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'


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
            object: Object,
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup,DateInputFormGroup,buttonAdd
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
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