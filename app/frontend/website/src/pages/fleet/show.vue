<!--
    This page shows a certain fleet in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t('fleet.fleet') | capitalize}} {{fleet.name}} </h1>
        </div>
        <div v-for="subfleet in subfleets">
            <div v-if="subfleet.vehicles.length > 0">
                <h3>{{subfleet.type.name | capitalize }}</h3>
                <list-component v-for="vehicle in subfleet.vehicles"
                                v-if="vehicle"
                                :object="vehicle"
                                :visibleKeys="new Array('brand','model', 'licensePlate')"
                                :remove="deleteVehicle"
                                edit="edit_vehicle"
                                show="vehicle"
                                :key="vehicle.id">
                </list-component>
            </div>
        </div>
        <button-add :route="{name: 'new_vehicle', params: {fleet_id: fleet.id}}"></button-add>
    </div>
</template>
<script>
    import listComponent from '../../assets/listComponent.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            listComponent, buttonAdd
        },
        created() {
            let id = this.$route.params.id
            this.fetchFleet({id: id})
            let p1 = this.fetchVehicles({fleetId: id})
            let p2 = this.fetchVehicleTypes()
            Promise.all([p1, p2]).then(values => {
                this.getSubfleets({
                    vehicles: values[0],
                    vehicleTypes: values[1]
                })
            })
        },
        computed: {
            ...mapGetters([
                'fleet',
                'subfleets'
            ])
        },
        methods: {
            ...mapActions([
                'getSubfleets',
                'fetchFleet',
                'fetchVehicleTypes',
                'fetchVehicles',
                'deleteVehicle'
            ])
        }
    }
</script>
<style>
    .table-button {
        margin-top: 20px;
        margin-right: 10px;
    }
    h3 {
        margin-top: 40px;
    }

</style>
