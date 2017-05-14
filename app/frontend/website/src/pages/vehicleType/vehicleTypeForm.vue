<template>
    <abstract-form :actions="actions" :resource="resource" :customSubmit="submit" :back="back">
        <vehicle-type-form-input v-if="vehicleType" :vehicleType="vehicleType"></vehicle-type-form-input>
        <div v-if="commissions.length>0" >
            <h2>{{$t('commission.commissions')}}</h2>
            <commission-form-input :commissions="commissions"></commission-form-input>
        </div>
    </abstract-form>
</template>
<script>
    import resources from '../../constants/resources'
    import vehicleTypeFormInput from './vehicleTypeFormInput.vue'
    import commissionFormInput from '../commission/commissionFormInput.vue'
    import AbstractForm from '../../assets/form/AbstractForm.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                back: {name:resources.VEHICLE_TYPE.name.plural()},
            }
        },
        components: {
            commissionFormInput, vehicleTypeFormInput, AbstractForm
        },
        props: {
            submit: Function, //Custom function for submitting this form
            actions: Object,
            commissions: Array,
            vehicleType: Object
        }
    }
</script>