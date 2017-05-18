<!--
    This page shows a certain fleet in detail.

   @param id: The id of the fleet to be shown.
-->
<template>
    <div v-if="fleet">
        <div class="page-header">
            <h1>
                {{fleet.name}} <span v-if="fleet.companyName">- {{fleet.companyName }}</span>
                <button-add :resource="resource" :params="{fleetId: fleet.id}"></button-add>
                <button-link v-if="hasPermissionForRoute('import_vehicles')" buttonId="import" buttonClass="pull-right btn btn-primary"
                             :route="{name: 'import_vehicles', params: {fleetId: id}}">
                    {{$t('vehicle.import') | capitalize}} {{$t('vehicle.vehicles')}}
                </button-link>
                <button-link v-if="hasPermissionForRoute('fleet_logs')" buttonId="log" buttonClass="btn btn-default pull-right"
                             :route="{name: 'fleet_logs'}">
                    {{$t('log.log') | capitalize}}
                </button-link>
            </h1>
            <h4>
                <span v-if="fleet.totalCost">  {{$t('fleet.totalCost')|capitalize}}: €{{fleet.totalCost }}</span>
                <span v-if="fleet.totalTax">  |  {{$t('fleet.totalTax')|capitalize}}:  €{{fleet.totalTax }}</span>
            </h4>
        </div>
        <abstract-search-form :resource="resource" :filters="filters" :searchFunction="searchVehicles">
            <vehicle-search-input :vehicle="filters"></vehicle-search-input>
        </abstract-search-form>
        <div v-for="subfleet in subfleets">
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
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import VehicleSearchInput from '../vehicle/VehicleSearchInput.vue'
    import ImportVehicles from '../vehicle/import.vue'

    export default {
        data(){
            return {
                filters: {},
                resource: resources.VEHICLE
            }
        },
        components: {
            listComponent, buttonAdd, buttonBack, buttonLink, AbstractSearchForm, VehicleSearchInput, ImportVehicles
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
            }, () => this.setLoading({loading: false}));
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
            }, () => this.setLoading({loading: false}))
        },
        computed: {
            ...mapGetters([
                'fleet',
                'subfleets',
                'vehicleTypes',
                'hasPermissionForRoute'
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
                'setLoading'
            ]),
            searchVehicles({filters}){
                filters.fleet = this.id
                let p1 = this.fetchVehiclesBy({filters}).then(vehicles => {
                    this.setVehicleInsurances(vehicles).then(ve => {
                        this.getSubfleets({
                            vehicles: ve,
                            vehicleTypes: this.vehicleTypes
                        }).then(() => {
                            this.setLoading({loading: false })
                        })
                    });
                })
            },
            listObject(vehicles) {
                var listObj = {};
                listObj.headers = ['brand','model', 'licensePlate','sureties','totalCost','totalTax'];
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
                        this.fleet.totalCost=0;
                        this.fleet.totalTax=0;
                        for (let i in vehicles) {
                            vehicles[i].sureties = "";
                            vehicles[i].totalCost=0;
                            vehicles[i].totalTax=0;
                            for (let j in vi[i]) {
                                if (vi[i][j].suretyType) {
                                    vehicles[i].sureties = vehicles[i].sureties + this.$t('suretyTypes.' + vi[i][j].suretyType).capitalize() + " ";
                                    vehicles[i].totalCost=vehicles[i].totalCost+vi[i][j].cost;
                                    vehicles[i].totalTax=vehicles[i].totalTax+vi[i][j].tax;
                                }
                            }
                            this.fleet.totalCost=this.fleet.totalCost+vehicles[i].totalCost;
                            this.fleet.totalTax=this.fleet.totalTax+vehicles[i].totalTax;
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
    #import {
        margin-right: 10px;
    }
</style>
