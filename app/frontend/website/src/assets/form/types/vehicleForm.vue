<!--
    This is fleetForm.vue.vue for creating/updating a vehicle.
    The form accepts the old vehicle and an update or create function.
-->
<template>
    <form-component>
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

        <div class="row">
            <button-action @click="proceed" buttonClass="btn btn-success btn-md"><i class="fa fa-check"></i></button-action>
            <button-link v-if="vehicle.fleet" :route="{name: 'fleet', params: {id: vehicle.fleet}}" buttonClass="btn btn-danger btn-md">
                <i  class="fa fa-times"></i>
            </button-link>
            <button-link v-else :route="{name: 'fleets'}" buttonClass="btn btn-danger btn-md">
                <i  class="fa fa-times"></i>
            </button-link>
        </div>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../formInput.vue'
    import formSelect from '../formSelect.vue'
    import buttonLink from '../../buttons/buttonLink.vue'
    import buttonAction from '../../buttons/buttonAction.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            formComponent, formInput, formSelect, buttonLink, buttonAction
        },
        props: {
            vehicle: Object, //Vehicle which should be created/updated with this fleetForm.vue.
            submit: Function //Submit function to create/update the vehicle.
        },
        created() {
            this.fetchVehicleTypes();
            this.fetchClients()
        },
        computed: {
            ...mapGetters([
                'clients',
                'vehicleTypes'
            ])
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