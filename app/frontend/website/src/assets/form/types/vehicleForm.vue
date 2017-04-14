<!--
    This page is used to generate a form for a user.
-->
<template>
    <form-component v-if="vehicle" :actions="actions" :resource="resource" :object="vehicle">
        <vehicle-form-input :vehicle="vehicle"></vehicle-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import vehicleFormInput from './vehicleFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE,
            }
        },
        components: {
            formComponent, vehicleFormInput
        },
        props: {
            actions: Object,
            oldVehicle: Object
        },
        computed: {
            vehicle(){
                if(this.oldVehicle === null) {
                    return {address: {}}
                }
                else {
                    return this.oldVehicle
                }
            }
        },
        methods: {
            proceed(){
                this.$store.dispatch(action + 'Vehicle').then(() => {
                    this.submit(this.vehicle).then(() => {
                        this.$router.push({name: 'vehicles'})
                    })
                })

            }
        }
    }
</script>
