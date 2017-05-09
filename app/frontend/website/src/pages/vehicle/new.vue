<!--
    This page is used to create a new vehicle.

    @param fleetId (optional): The fleetId of  the new vehicle
-->
<template>
    <abstract-form :actions="actions" :object="vehicle" :back="back" :resource="resource">
        <form-input :vehicle="vehicle"></form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.VEHICLE,
                vehicle: {},
                back:{}
            }
        },
        props:{
            fleetId:String
        },
        created(){
            this.back={name:resources.FLEET.name,params:{id: this.fleetId}};
            this.vehicle={fleet:this.fleetId}

        },
        components: {
            abstractForm,formInput
        }
    }
</script>