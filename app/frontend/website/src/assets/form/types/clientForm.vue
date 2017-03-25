<!--
    This page is used to generatfleetForm.vue.vue for a client.
-->
<template>

    <form-component v-if="client && client.address">
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

        <div class="row">
            <button-success @click="proceed"></button-success>
            <button-fail :route="{name: 'clients'}"></button-fail>
        </div>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../elements/formInput.vue'
    import buttonFail from '../../buttons/buttonFail.vue'
    import buttonSuccess from '../../buttons/buttonSuccess.vue'

    export default {
        components: {
            formComponent, formInput, buttonFail, buttonSuccess
        },
        props: {
            submit: Function, //Function to create the fleet.
            oldClient: Object
        },
        computed: {
            client(){
                if(this.oldClient === undefined) {
                    return {address: {}}

                }
                else {
                    return this.oldClient
                }
            }
        },
        methods: {
            proceed(){
                this.submit({client: this.client}).then(() => {
                    this.$router.push({name: 'clients'})
                })
            }
        }
    }
</script>