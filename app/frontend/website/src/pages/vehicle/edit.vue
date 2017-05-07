<!--
    This page is used to edit a certain vehicle.

    @param id: The id of the vehicle which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="vehicle" :back="back" :resource="resource">
        <form-input :vehicle="vehicle"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from '../../assets/form/types/vehicleFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE,
                actions: actions.UPDATE,
                vehicle: {},
                back:{name: 'fleet'}
            }
        },
        created(){
            if(this.id){
                this.fetchVehicle({id: this.id}).then(o => {
                    this.vehicle = o;
                    this.back.params={id: this.vehicle.fleet};
                })
            }
        },
        components: {
            abstractForm,formInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchVehicle'
            ])

        }
    }
</script>

