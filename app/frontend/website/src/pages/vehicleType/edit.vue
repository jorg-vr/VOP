<template>
    <abstract-form :actions="actions" :object="vehicleType" :back="back" :resource="resource">
        <form-input :vehicleType="vehicleType"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleTypeForm.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                actions: actions.UPDATE,
                vehicleType: {address:{}},
                back:{name:resources.VEHICLE_TYPE.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchVehicleType({id: this.id}).then(o => {
                    this.vehicleType = o;
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
                'fetchVehicleType'
            ])

        }
    }
</script>