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
                :object="contract" name="startDate" :text="$t('insurance.startDate')">
        </date-input-form-group>

        <!-- End Date -->
        <date-input-form-group
                :object="contract" name="endDate" :text="$t('insurance.endDate')">
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