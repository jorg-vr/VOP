<!--
    This page shows a certain fleet in detail.

   @param id: The id of the fleet to be shown.
-->
<template>
    <div v-if="fleet">
        <div class="page-header">
            <h1>{{fleet.name}} <span v-if="fleet.companyName">- {{fleet.companyName }}</span>
                <button-add :resource="resource" :params="{fleetId: fleet.id}"></button-add>
            </h1>
        </div>
        <vehicle-search-bar @search="updateSubfleets" @advancedSearch="updateSubfleetsAdvanced"></vehicle-search-bar>


        <div v-for="subfleet in filteredSubfleets">
            <div v-if="subfleet.vehicles.length > 0">
                <h3>{{subfleet.type.name | capitalize }}</h3>
                <list-component :resource="resource" :listObject="listObject(subfleet.vehicles)"></list-component>
            </div>
        </div>
        <button-back :route="{name: 'fleets'}"></button-back>
    </div>
</template>
<script>
    import resources from '../../constants/resources'
    import listComponent from '../../assets/list/listComponent.vue'
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
            this.setLoading({loading: true });
            this.fetchFleet({id: id}).then(fleet => {
                this.fetchClient({id: fleet.company}).then(client => {
                    this.addClientName({client})
                })
            });
            let p1 = this.fetchVehiclesBy({filters: {fleet: id}});
            let p2 = this.fetchVehicleTypes();
            Promise.all([p1, p2]).then(values => {
                this.setVehicleInsurances(values[0]).then(ve =>{
                    this.getSubfleets({
                        vehicles: ve,
                        vehicleTypes: values[1]
                    }).then(() => {
                        this.setLoading({loading: false })
                    })
                });
            })
        },
        computed: {
            ...mapGetters([
                'fleet',
                'subfleets',
                'filteredSubfleets',
                'getSubfleetsByAll',
                'getSubfleetsByAllAdvanced'
            ]),
        },
        methods: {
            ...mapActions([
                'getSubfleets',
                'fetchClient',
                'fetchFleet',
                'fetchVehicleTypes',
                'fetchVehiclesBy',
                'fetchInsurancesBy',
                'deleteVehicle',
                'addClientName'
            ]),

            ...mapMutations([
                'updateFilteredSubfleets',
                'setLoading'
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
            },
            listObject(vehicles) {
                var listObj = {};
                listObj.headers = ['brand','model', 'licensePlate','sureties'];
                listObj.values = vehicles;
                return listObj;
            },
            setVehicleInsurances(vehicles){
                return new Promise((resolveSuccess, resolveFailure) => {
                    let p=[];
                    for (let i in vehicles) {
                        p[i]=this.fetchInsurancesBy({filters: {vehicleId: vehicles[i].id}});
                    }
                    Promise.all(p).then(vi=> {
                        for (let i in vehicles) {
                            vehicles[i].sureties = "";
                            for (let j in vi[i]) {
                                if (vi[i][j].suretyType) {
                                    vehicles[i].sureties = vehicles[i].sureties + this.$t('suretyTypes.' + vi[i][j].suretyType).capitalize() + " ";
                                }
                            }
                        }
                        resolveSuccess(vehicles);
                    }).catch(vi=>{resolveFailure(vehicles)});
                });
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
