<!--
All of the fields for user input for the vehicle form

@param vehicle: This object will be configured with the input of this component.
-->
<template>
    <div>
        <form-input :placeholder="$t('vehicle.licensePlate') | capitalize" :label="$t('vehicle.licensePlate') | capitalize"
                    v-model="vehicle.licensePlate"></form-input>

        <form-input :placeholder="$t('vehicle.vin') | capitalize" :label="$t('vehicle.vin') | capitalize"
                    v-model="vehicle.vin"></form-input>

        <form-input :placeholder="$t('vehicle.brand') | capitalize" :label="$t('vehicle.brand') | capitalize"
                    v-model="vehicle.brand"></form-input>

        <form-input :placeholder="$t('vehicle.model') | capitalize" :label="$t('vehicle.model') | capitalize"
                    v-model="vehicle.model"></form-input>

        <form-select optionKey="name" :options="vehicleTypes" :label="$t('vehicle.vehicleType') | capitalize "
                     v-model="vehicle.type"></form-select>

        <form-input :placeholder="$t('vehicle.mileage') | capitalize" :label="$t('vehicle.mileage') | capitalize"
                    v-model="vehicle.mileage"></form-input>

        <form-input :placeholder="$t('vehicle.year') | capitalize" :label="$t('vehicle.year') | capitalize"
                    v-model="vehicle.year"></form-input>

        <form-select optionKey="name" :options="clients" :label="$t('vehicle.leasingCompany') | capitalize "
                     v-model="vehicle.leasingCompany"></form-select>
    </div>

</template>
<script>
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    import clientTypes from '../../../constants/clientTypes'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        props: {
            vehicle: Object,
        },
        components: {
            formInput, formSelect
        },
        created(){
            //Fetch options for the select items.
            this.fetchClientsBy({type: clientTypes.LEASING_COMPANY.value})
            this.fetchVehicleTypes()
        },
        computed: {
            ...mapGetters([
                'clients',
                'vehicleTypes'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClientsBy',
                'fetchVehicleTypes'
            ])
        }
    }
</script>