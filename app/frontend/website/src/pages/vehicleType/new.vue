<template>
    <abstract-form :actions="actions" :object="vehicleType" :back="back" :resource="resource">
        <form-input :vehicleType="vehicleType"></form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleTypeForm.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.VEHICLE_TYPE,
                vehicleType: {},
                back:{name:resources.VEHICLE_TYPE.name.plural()}
            }
        },
        components: {
            abstractForm,formInput
        }
    }
</script>