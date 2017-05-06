<!--
All of the fields for user input for the client form

@param client: This object will be configured with the input of this component.
-->
<template>
    <div v-if="client">
        <form-input :object="client" name="name" :text="$t('client.name')" :rules="'required'"></form-input>
        <form-select :object="client" name="type" :text="$t('client.type')" :rules="'required'" :options="clientTypes"></form-select>
        <form-input :object="client.address" name="country" :text="$t('address.country')" :rules="'required|length:2'"></form-input>
        <form-input :object="client.address" name="city" :text="$t('address.city')" :rules="'required'"></form-input>
        <form-input :object="client.address" name="street" :text="$t('address.street')" :rules="'required'"></form-input>
        <form-input :object="client.address" name="postalCode" :text="$t('address.postalCode')" :rules="'required|numeric'"></form-input>
        <form-input :object="client.address" name="houseNumber" :text="$t('address.houseNumber')" :rules="'required|numeric'"></form-input>
        <form-input :object="client" name="vatNumber" :text="$t('client.vatNumber')" :rules="'required|min:8'"></form-input>
        <form-input :object="client" name="phoneNumber" :text="$t('client.phoneNumber')" :rules="'required|min:6'"></form-input>
    </div>
</template>"
<script>
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    import clientTypes from '../../../constants/clientTypes'
    export default {
        data(){
            return {
                clientTypes: $.map(clientTypes, function (value, index) {
                    return [value]
                })
            }
        },
        props: {
            object: Object,
        },
        components: {
            formInput, formSelect
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