<!--
All of the fields for contract input for the contract form

@param object: This object will be configured with the input of this component.
@param actions: Used to distinguish create from the edit action.
-->
<template>
    <div>
        <!-- Customer -->
        <select-input-form-group
                :object="contract" name="customer" optionPropertyName="id" visibleKey="name"
                :text="$t('clientTypes.customer')" :options="customers">
        </select-input-form-group>

        <!-- Insurance Company -->
        <select-input-form-group :object="contract" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                                 :text="$t('clientTypes.insuranceCompany')" :options="insuranceCompanies">
        </select-input-form-group>

        <!-- Start Date -->
        <date-input-form-group
                :object="contract" name="startsBefore" :text="$t('insurance.startsBefore')">
        </date-input-form-group>

        <!-- Start Date -->
        <date-input-form-group
                :object="contract" name="startsOn" :text="$t('insurance.startsOn')">
        </date-input-form-group>

        <!-- Start Date -->
        <date-input-form-group
                :object="contract" name="startsAfter" :text="$t('insurance.startsAfter')">
        </date-input-form-group>

        <!-- End Date -->
        <date-input-form-group
                :object="contract" name="endsBefore" :text="$t('insurance.endsBefore')">
        </date-input-form-group>

        <!-- End Date -->
        <date-input-form-group
                :object="contract" name="endsOn" :text="$t('insurance.endsOn')">
        </date-input-form-group>


        <!-- End Date -->
        <date-input-form-group
                :object="contract" name="endsAfter" :text="$t('insurance.endsAfter')">
        </date-input-form-group>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'

    export default {
        data(){
            return {
                customers:[],
                insuranceCompanies:[]
            }
        },
        props: {
            actions: Object,
            contract: Object,
        },
        components: {
            SelectInputFormGroup,DateInputFormGroup
        },
        computed: {
            ...mapGetters([
                'clients'
            ]),
        },
        methods: {
            ...mapActions([
                'fetchClientsBy'
            ])
        },
        created(){
            // fetch all customers
            this.fetchClientsBy({filters: {type: clientTypes.CUSTOMER.type}}).then(clients => {
                this.customers = clients
            })
            // fetch all insurance companies
            this.fetchClientsBy({filters: {type: clientTypes.INSURANCE_COMPANY.type}}).then(clients => {
                this.insuranceCompanies = clients
            })
        }
    }
</script>