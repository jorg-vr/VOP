<template>
    <abstract-form :actions="actions" :object="vehicleType" :back="back" :resource="resource">
        <form-input :vehicleType="vehicleType"></form-input>
        <commissionForm :commissions="commissions"></commissionForm>
    </abstract-form>
</template>
<script>
    import {mapActions,mapGetters} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleTypeForm.vue'
    import commissionForm from '../commission/commissionForm.vue'
    import * as locations from '../../constants/locations'

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
                this.fetchCommissions({ids:{'resource':locations.VEHICLE_TYPE,'resourceId':this.id}});
            }
        },
        components: {
            abstractForm,formInput,commissionForm
        },
        props: {
            id: String
        },
        computed: {
            ...mapGetters([
                'commissions'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicleType',
                'fetchCommissions'
            ])

        }
    }
</script>