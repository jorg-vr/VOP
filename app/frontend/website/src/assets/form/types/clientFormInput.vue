<template>
    <div>
        <form-input :placeholder="$t('common.name') | capitalize" :label="$t('common.name') | capitalize"
                    v-model="client.name"></form-input>

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
    </div>
</template>
<script>
    import formInput from '../elements/formInput.vue'

    export default {
        data(){
            types: {
                {value: "CUSTOMER", name: ""},
                {value: "LEASING_COMPANY", }
            }
        },
        props: {
            client: Object
        },
        components: {
            formInput
        }


    }
</script>