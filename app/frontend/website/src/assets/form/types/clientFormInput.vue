<!--
All of the fields for user input for the client form

@param client: This object will be configured with the input of this component.


TODO: ADD VALIDATIONS
See: https://vuejs.org/v2/guide/class-and-style.html
See: http://vee-validate.logaretm.com/
-->
<template>
    <div>
        <form-input :object="client" name="name" :text="$t('client.name')" :rules="'required'"></form-input>
        <form-select :object="client" name="type" :text="$t('client.type')" :rules="'required'" :options="clientTypes"></form-select>
        <form-input :object="client.address" name="country" :text="$t('address.country')" :rules="'required|alpha_dash|min:2|max:2'"></form-input>
        <form-input :object="client.address" name="city" :text="$t('address.city')" :rules="'required|alpha_dash'"></form-input>
        <form-input :object="client.address" name="street" :text="$t('address.street')" :rules="'required'"></form-input>
        <form-input :object="client.address" name="postalCode" :text="$t('address.postalCode')" :rules="'required'"></form-input>
        <form-input :object="client.address" name="houseNumber" :text="$t('address.houseNumber')" :rules="'required'"></form-input>

        <!--
        <form-input :placeholder="$t('address.country') | capitalize" :label="$t('address.country') | capitalize"

                    v-model="client.address.country"></form-input>

        <form-input :placeholder="$t('address.city') | capitalize" :label="$t('address.city') | capitalize"
                    v-model="client.address.city"></form-input>

        <form-input :placeholder="$t('address.postalCode') | capitalize" :label="$t('address.postalCode') | capitalize"
                    v-model="client.address.postalCode"></form-input>

        <form-input :placeholder="$t('address.street') | capitalize" :label="$t('address.street') | capitalize"
                    v-model="client.address.street"></form-input>

        <form-input :placeholder="$t('address.houseNumber') | capitalize" :label="$t('address.houseNumber') | capitalize"
                    v-model="client.address.houseNumber"></form-input>

        <form-input :placeholder="$t('client.vatNumber') | capitalize" :label="$t('client.vatNumber') | capitalize"
                    v-model="client.vatNumber"></form-input>

        <form-input :placeholder="$t('client.phoneNumber') | capitalize" :label="$t('client.phoneNumber') | capitalize"
                    v-model="client.phoneNumber"></form-input>
                    -->

    </div>

</template>
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
            client: Object,
        },
        components: {
            formInput, formSelect
        }
    }


</script>