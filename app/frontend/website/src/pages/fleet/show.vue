<!--
    This page shows a certain fleet in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t('fleet.fleet') | capitalize}} {{fleet.name}} - {{fleet.companyName }} </h1>
        </div>
        <vehicle-search-bar @search="updateSubfleets" @advancedSearch="updateSubfleetsAdvanced"></vehicle-search-bar>
        <div v-for="subfleet in filteredSubfleets">
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
        <button-back :route="{name: 'fleets'}"></button-back>
        <button-add :route="{name: 'new_vehicle', params: {fleetId: fleet.id}}"></button-add>
    </div>
</template>
<script>
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import vehicleSearchBar from '../../assets/search/types/vehicleSearchBar.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        components: {
            listComponent, buttonAdd, vehicleSearchBar, buttonBack
        },
        props: {
            id: String
        },
        created() {
            let id = this.id
            this.fetchFleet({id: id}).then(fleet => {
                this.fetchClient({id: fleet.company}).then(client => {
                    this.addClientName({client})
                })
            })
            let p1 = this.fetchVehicles({fleetId: id})
            let p2 = this.fetchVehicleTypes()
            Promise.all([p1, p2]).then(values => {
                this.getSubfleets({
                    vehicles: values[0],
                    vehicleTypes: values[1]
                })
                this.updateFilteredSubfleets({subfleets: this.subfleets})
            })
        },
        computed: {
            ...mapGetters([
                'fleet',
                'subfleets',
                'filteredSubfleets',
                'getSubfleetsByAll',
                'getSubfleetsByAllAdvanced'
            ])
        },
        methods: {
            ...mapActions([
                'getSubfleets',
                'fetchClient',
                'fetchFleet',
                'fetchVehicleTypes',
                'fetchVehicles',
                'deleteVehicle',
                'addClientName'
            ]),

            ...mapMutations({
                updateFilteredSubfleets: 'UPDATE_FILTERED_SUBFLEETS'
            }),

            updateSubfleets(value){
                if(value!==''){
                    this.updateFilteredSubfleets({subfleets: this.getSubfleetsByAll(value)})
                }
                else {
                    this.updateFilteredSubfleets({subfleets: this.subfleets})
                }
            },
            updateSubfleetsAdvanced(filterSubfleet){
                this.updateFilteredSubfleets({subfleets: this.getSubfleetsByAllAdvanced(filterSubfleet)})
            }
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
