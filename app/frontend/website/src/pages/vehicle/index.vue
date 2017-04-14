<!--
    This page shows all vehicles in the database.
    From this page a new vehicle can be added or an existing vehicle can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>{{$t("vehicle.vehicles") | capitalize }}</h1>
        </div>
        <vehicle-search-bar @advancedSearch="updateVehiclesAdvanced" :vehicleTypes="vehicleTypes"></vehicle-search-bar>
        <!-- Render an info-pane for every vehicle. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="vehicle in filteredVehicles"
                        v-if="vehicle"
                        :object="vehicle"
                        :visibleKeys="new Array('brand','model', 'licensePlate')"
                        :remove="deleteVehicle"
                        edit="edit_vehicle"
                        show="vehicle"
                        :key="vehicle.id">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import vehicleSearchBar from '../../assets/search/types/vehicleSearchForm.vue'

    export default {
        components: {
            listComponent, buttonAdd, vehicleSearchBar
        },
        created() {
            this.fetchVehicleTypes()
        },
        computed: {
            ...mapGetters([
                'vehicleTypes',
                'filteredVehicles'
            ])
        },
        methods: {
            ...mapActions([
                'deleteVehicle',
                'fetchVehicleTypes',
                'fetchVehiclesBy'
            ]),

            ...mapMutations([
                'setFilteredVehicles'
            ]),

            updateVehiclesAdvanced(filterVehicle){
                this.setFilteredVehicles(this.fetchVehiclesBy({vehicle: filterVehicle}))
            }
        }
    }
</script>
