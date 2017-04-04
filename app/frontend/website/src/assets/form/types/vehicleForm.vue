<!--
    This is fleetForm.vue.vue for creating/updating a vehicle.
    The form accepts the old vehicle and an update or create function.
-->
<template>
    <form-component v-if="vehicle" @submit="proceed" :failroute="{name: 'fleet', params: {id: vehicle.fleet}}">
        <vehicle-form-input :vehicle="vehicle"></vehicle-form-input>
    </form-component>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import vehicleFormInput from './vehicleFormInput.vue'

    export default {
        components: {
            formComponent, vehicleFormInput
        },
        props: {
            submit: Function, //Submit function to create/update the vehicle.
            oldVehicle: Object, //Vehicle which should be created/updated with this form
            fleetId: String
        },
        computed: {
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