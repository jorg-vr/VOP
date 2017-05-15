<!--
All of the fields for user input for the client form

@param client: This object will be configured with the input of this component.
-->
<template>
    <div v-if="client">
        <text-input-form-group :object="client" name="name" :text="$t('client.name')"></text-input-form-group>
        <select-input-form-group :object="client" name="type" optionPropertyName="type" visibleKey="translation"
                                 :text="$t('client.type')" :options="clientTypes">
        </select-input-form-group>
        <text-input-form-group :object="client.address" name="country" :text="$t('address.country')"></text-input-form-group>
        <text-input-form-group :object="client.address" name="city" :text="$t('address.city')"></text-input-form-group>
        <text-input-form-group :object="client.address" name="street" :text="$t('address.street')"></text-input-form-group>
        <text-input-form-group :object="client.address" name="postalCode" :text="$t('address.postalCode')"></text-input-form-group>
        <text-input-form-group :object="client.address" name="houseNumber" :text="$t('address.houseNumber')"></text-input-form-group>
        <text-input-form-group :object="client" name="vatNumber" :text="$t('client.vatNumber')"></text-input-form-group>
        <text-input-form-group :object="client" name="phoneNumber" :text="$t('client.phoneNumber')"></text-input-form-group>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'

    export default {
        data() {
            return {
                clientTypes: $.map(clientTypes, function (value, index) {
                    return [value]
                })
            }
        },
        props: {
            client: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup
        }
    }
</script>