<template>
    <vehicle-type-form :actions="actions" :submit="submit" :vehicleType="vehicleType" :commissions="commissions"></vehicle-type-form>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import * as locations from '../../constants/locations'
    import actions from '../../constants/actions'
    import VehicleTypeForm from './VehicleTypeForm.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                ids: {'resource':locations.VEHICLE_TYPE,'resourceId':this.id}
            }
        },
        created(){
            this.fetchCommissions({ids: this.ids})
            this.fetchVehicleType({id: this.id})
        },
        components: {
            VehicleTypeForm
        },
        props: {
            id: String
        },
        computed: {
            ...mapGetters([
                'vehicleType',
                'commissions'
            ])
        },
        methods: {
            submit(){
                return new Promise((resolve, reject) => {
                    let p1 = this.updateCommission({resource: this.commissions, ids: this.ids})
                    let p2 = this.updateVehicleType({resource: this.vehicleType})
                    Promise.all([p1,p2]).then(() => resolve(), () => reject())
                })
            },
            ...mapActions([
                'fetchCommissions',
                'fetchVehicleType',
                'updateCommission',
                'updateVehicleType'
            ])
        }
    }
</script>