<template>
    <vehicle-type-form :actions="actions" :submit="submit" :vehicleType="vehicleType" :commissions="commissions"></vehicle-type-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import * as locations from '../../constants/locations'
    import actions from '../../constants/actions'
    import VehicleTypeForm from './VehicleTypeForm.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        data(){
            return {
                vehicleType: {},
                actions: actions.CREATE,
            }
        },
        components: {
            VehicleTypeForm
        },
        props: {
            id: String
        },
        computed: {
            commissions(){
                let commissions = []
                for(let i=0;i<suretyTypes.length;i++){
                    commissions[i]={
                        suretyType: suretyTypes[i].name,
                        commission: 0
                    }
                }
                return commissions
            }
        },
        methods: {
            submit(){
                return new Promise((resolve, reject) => {
                    this.createVehicleType({resource:this.vehicleType}).then(vehicleType =>{
                        let params = {resource: this.commissions, ids: {'resource': locations.VEHICLE_TYPE,'resourceId':vehicleType.id}}
                        this.updateCommission(params).then(() => {
                            resolve()
                        })
                    }, () => reject())
                })

            },
            ...mapActions([
                'updateCommission',
                'createVehicleType'
            ])
        }
    }
</script>

