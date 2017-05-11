<template>
    <div>
        <abstract-form :actions="actions" :object="vehicleType" :back="back" :resource="resource">
            <form-input :vehicleType="vehicleType"></form-input>
        </abstract-form>
        <div class="col-lg-12">
        <commission-edit :id="id" :loc="loc" :back="back" ></commission-edit>
        </div>
    </div>
</template>
<script>
    import {mapActions,mapGetters} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleTypeForm.vue'
    import commissionEdit from '../commission/edit.vue'
    import * as locations from '../../constants/locations'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                actions: actions.UPDATE,
                vehicleType: {address:{}},
                back:{name:resources.VEHICLE_TYPE.name.plural()},
                loc:locations.VEHICLE_TYPE
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
            abstractForm,formInput,commissionEdit
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