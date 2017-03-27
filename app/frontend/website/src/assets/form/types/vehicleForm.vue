<!--
    This is fleetForm.vue.vue for creating/updating a vehicle.
    The form accepts the old vehicle and an update or create function.
-->
<template>
    <form-component v-if="vehicle" @submit="proceed" :failroute="{name: 'fleet', params: {id: vehicle.fleet}}">
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
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    import buttonFail from '../../buttons/buttonFail.vue'
    import buttonSuccess from '../../buttons/buttonSuccess.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            formComponent, formInput, formSelect, buttonFail, buttonSuccess
        },
        props: {
            submit: Function, //Submit function to create/update the vehicle.
            oldVehicle: Object, //Vehicle which should be created/updated with this form
            fleetId: String
        },
        created() {
            this.fetchVehicleTypes()
            this.fetchClients()
        },
        computed: {
            ...mapGetters([
                'clients',
                'vehicleTypes'
            ]),
            vehicle(){
                if(this.oldVehicle === undefined) {
                    return {fleet: this.fleetId}
                }
                else {
                    return this.oldVehicle
                }
            }
        },
        methods: {
            ...mapActions([
                'fetchVehicleTypes',
                'fetchClients'
            ]),
            proceed(){
                this.submit({vehicle: this.vehicle}).then(vehicle => {
                    if(vehicle.fleet){
                        this.$router.push({name: 'fleet', params: {id: vehicle.fleet}})
                    }
                    else {
                        this.$router.push({name: 'fleets'})
                    }
                })
            }
        }
    }

</script>