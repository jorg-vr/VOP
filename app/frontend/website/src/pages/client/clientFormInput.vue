<!--
All of the fields for user input for the client form

@param client: This object will be configured with the input of this component.
-->
<template>
    <div v-if="client">
        <text-input-form-group :object="client" name="name" :text="$t('client.name')" :rules="'required'"></text-input-form-group>
        <select-input-form-group :object="client" name="type" :text="$t('client.type')" :rules="'required'"
                                 :options="clientTypes" visibleKey="translation">
        </select-input-form-group>
        <country-select :object="client.address" name="country" :text="$t('address.country')" :rules="'required'"></country-select>
        <text-input-form-group :object="client.address" name="city" :text="$t('address.city')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="client.address" name="street" :text="$t('address.street')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="client.address" name="postalCode" :text="$t('address.postalCode')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="client.address" name="houseNumber" :text="$t('address.houseNumber')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="client" name="vatNumber" :text="$t('client.vatNumber')" :rules="'required|min:8'"></text-input-form-group>
        <text-input-form-group :object="client" name="phoneNumber" :text="$t('client.phoneNumber')" :rules="'required|min:6'"></text-input-form-group>
        <select-input-form-group :object="client" name="paymentPeriod" :text="$t('client.paymentPeriod')" :rules="'required'"
                                 :options="periods" optionPropertyName="periodSelect" visibleKey="translation">
        </select-input-form-group>
        <select-input-form-group :object="client" name="facturationPeriod" :text="$t('client.facturationPeriod')" :rules="'required'"
                                 :options="periods" optionPropertyName="periodSelect" visibleKey="translation">
        </select-input-form-group>
    </div>
</template>
<script>
    import clientTypes from '../../constants/clientTypes'
    import periods from '../../constants/periods'
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import CountrySelect from './CountrySelect.vue'

    export default {
        data(){
            return {
                clientTypes: $.map(clientTypes, function (value, index) {
                    return [value]
                }),
                periods : periods
            }
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        props: {
            object: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup, CountrySelect
        },
        computed: {
            client(){
                if(this.object) {
                    return this.object
                }
                else {
                    return {address: {}}
                }
            }
        }
    }
</script>