<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
        <form-input :placeholder="$t('insurance.type') | capitalize" :label="$t('insurance.type') | capitalize"
                    v-model="insurance.type"></form-input>

        <form-input :placeholder="$t('insurance.vehicle') | capitalize" :label="$t('insurance.vehicle') | capitalize"
                    v-model="insurance.vehicle"></form-input>

        <form-input :placeholder="$t('insurance.company') | capitalize" :label="$t('insurance.company') | capitalize"
                    v-model="insurance.insuranceCompany"></form-input>

        <form-input :placeholder="$t('insurance.premium') | capitalize" :label="$t('insurance.premium') | capitalize"
                    v-model="insurance.premium"></form-input>

        <form-input :placeholder="$t('insurance.franchise') | capitalize" :label="$t('insurance.franchise') | capitalize"
                    v-model="insurance.franchise"></form-input>

        <form-input :placeholder="$t('insurance.startdate') | capitalize" :label="$t('insurance.startdate') | capitalize"
                    v-model="insurance.startDate"></form-input>

        <form-input :placeholder="$t('insurance.enddate') | capitalize" :label="$t('insurance.enddate') | capitalize"
                    v-model="insurance.endDate"></form-input>

    </div>
</template>
<script>
    import formInput from '../elements/formInput.vue'

    export default {
        props: {
            insurance: Object
        },
        components: {
            formInput
        }
    }
</script>