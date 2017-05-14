<!--
All of the fields for user input for the vehicle form

@param vehicle: This object will be configured with the input of this component.
-->
<template>
    <div>
        <text-input-form-group :object="vehicle" name="licensePlate" :text="$t('vehicle.licensePlate')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="vin" :text="$t('vehicle.vin')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="brand" :text="$t('vehicle.brand')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="model" :text="$t('vehicle.model')"></text-input-form-group>
        <select-input-form-group :object="vehicle" name="type" optionPropertyName="id" visibleKey="name"
                                 :text="$t('vehicle.vehicleType')" :options="vehicleTypes">
        </select-input-form-group>
        <text-input-form-group :object="vehicle" name="mileage" :text="$t('vehicle.mileage')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="year" :text="$t('vehicle.year')"></text-input-form-group>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        props: {
            vehicle: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup
        },
        created(){
            this.fetchVehicleTypes()
        },
        computed: {
            ...mapGetters([
                'vehicleTypes'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicleTypes'
            ])
        },

    }
</script>