<!--
    This page shows a certain fleet in detail.

   @param id: The id of the fleet to be shown.
-->
<template>
    <div v-if="fleet">
        <div class="page-header">
            <h1>{{$t('fleet.fleet') | capitalize}} {{fleet.name}} <span v-if="fleet.companyName">- {{fleet.companyName }}</span> </h1>
        </div>
        <vehicle-search-bar @search="updateSubfleets" @advancedSearch="updateSubfleetsAdvanced"></vehicle-search-bar>
        <div v-for="subfleet in filteredSubfleets">
            <div v-if="subfleet.vehicles.length > 0">
                <h3>{{subfleet.type.name | capitalize }}</h3>
                <list-component v-for="fleet in subfleet.vehicles"
                                v-if="fleet"
                                :resource="resource"
                                :object="fleet"
                                :visibleKeys="['brand','model', 'licensePlate']"
                                :key="fleet.id">
                </list-component>
            </div>
        </div>
        <button-back :route="{name: 'fleets'}"></button-back>
        <button-add :resource="resource" :params="{fleetId: fleet.id}"></button-add>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import vehicleSearchBar from '../../assets/search/types/vehicleSearchBar.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        data(){
            return {
                resource: resources.VEHICLE
            }
        },
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
            let p1 = this.fetchVehiclesByFleet({fleetId: id})
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
                'fetchVehiclesByFleet',
                'deleteVehicle',
                'addClientName'
            ]),

            ...mapMutations([
                'updateFilteredSubfleets'
            ]),

            updateSubfleets(value){
                if(value!==''){
                    this.updateFilteredSubfleets(this.getSubfleetsByAll(value))
                }
                else {
                    this.updateFilteredSubfleets(this.subfleets)
                }
            },
            updateSubfleetsAdvanced(filterSubfleet){
                this.updateFilteredSubfleets(this.getSubfleetsByAllAdvanced(filterSubfleet))
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
