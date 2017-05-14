<template>
    <abstract-form :actions="actions" :resource="resource" :customSubmit="submit" :back="back">
        <vehicle-type-form-input v-if="vehicleType" :vehicleType="vehicleType"></vehicle-type-form-input>
        <div  v-if="commissions.length>0" >
            <h2>{{$t('commission.commissions')}}</h2>
            <commission-form-input :commissions="commissions"></commission-form-input>
        </div>
    </abstract-form>
</template>
<script>
    import {mapActions,mapGetters} from 'vuex'
    import resources from '../../constants/resources'
    import vehicleTypeFormInput from './vehicleTypeFormInput.vue'
    import commissionFormInput from '../commission/commissionFormInput.vue'
    import * as locations from '../../constants/locations'
    import AbstractForm from '../../assets/form/AbstractForm.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                back: {name:resources.VEHICLE_TYPE.name.plural()},
                commissionIds: {'resource':locations.VEHICLE_TYPE,'resourceId':this.id}
            }
        },
        created(){
            this.fetchVehicleType({id: this.id});
            this.fetchCommissions({ids: this.commissionIds});
        },
        components: {
            commissionFormInput, vehicleTypeFormInput, AbstractForm
        },
        props: {
            actions: Object,
            id: String
        },
        computed: {
            ...mapGetters([
                'commissions', 'vehicleType'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicleType',
                'fetchCommissions',
            ]),
            submit(){
                return new Promise((resolve, reject) => {
                    let p1 = this.$store.dispatch(this.actions.name + 'Commission', {resource: this.commissions, ids: this.commissionIds})
                    let p2 = this.$store.dispatch(this.actions.name + 'VehicleType', {resource: this.vehicleType})
                    Promise.all([p1,p2]).then(() => resolve(), () => reject())
                })
            }
        }
    }
</script>