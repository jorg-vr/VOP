<!--
    This page shows a certain vehicle in detail.

    @param id: The id of the vehicle to be shown.
-->
<template>
    <div v-if="vehicle">
        <div class="page-header">
            <h1> {{$t('vehicle.vehicle') | capitalize }} {{vehicle.licensePlate}}</h1>
        </div>
        <div class="col-md-8">
            <table id="show-vehicle" class="table show-table">
                <tr>
                    <td>{{$t('vehicle.licensePlate') | capitalize }}</td>
                    <td>{{vehicle.licensePlate}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.vin') | capitalize }}</td>
                    <td>{{vehicle.vin}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.brand') | capitalize }}</td>
                    <td>{{vehicle.brand}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.model') | capitalize }}</td>
                    <td>{{vehicle.model}}</td>
                </tr>
                <tr v-if="vehicleType">
                    <td>{{$t('vehicle.vehicleType') | capitalize }}</td>
                    <td>{{vehicleType.name}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.mileage') | capitalize }}</td>
                    <td>{{vehicle.mileage}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.year') | capitalize }}</td>
                    <td>{{vehicle.year}}</td>
                </tr>
            </table>
        </div>
        <commissions :id="id" loc="vehicles" :back="back" ></commissions>
        <div class="col-md-12">
            <h3>
                {{$t("vehicle_insurance.vehicle_insurances") | capitalize }}
            </h3>
            <table class="table-hover table">
                <thead>
                <tr>
                    <th v-for="head in listObject.headers">
                        {{$t(resource.name + '.' + head).capitalize()}}
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="value in listObject.values" class="list-tr">
                    <td v-for="header in listObject.headers" class="clickable-td" @click="tdclick(value)">
                        {{value[header]}}
                    </td>
                    <td class="stretch">
                        <button-edit :resource="resource" :params="{id:value.id,contractId:value.contract}" ></button-edit>
                        <button-remove :resource="resource"  @click="tdshowModal(value.id)"></button-remove>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <button-link v-if="hasPermissionForRoute('vehicle_logs')" buttonClass="btn btn-default pull-left" buttonId="log"
                     :route="{name: 'vehicle_logs', params: {id: this.id}}">
            {{$t('log.log') | capitalize}}
        </button-link>
        <button-back v-if="vehicle.fleet" :route="{name: 'fleet', params: {id: vehicle.fleet}}"></button-back>
        <button-back v-else :route="{name: 'fleets'}"></button-back>
    </div>
</template>
<script>
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import {mapGetters, mapActions} from 'vuex'
    import listComponent from '../../assets/general/listComponent.vue'
    import resources from '../../constants/resources'
    import * as utils from '../../utils/utils'
    import commissions from '../commission/collapse.vue'

    export default {
        data(){
            return {
                resource: resources.INSURANCE,
                back:{name:resources.VEHICLE.name,params:{id:this.id}}
            }
        },
        components: {
            buttonBack,listComponent, buttonLink,buttonEdit,buttonRemove,commissions
        },
        props: {
            id: String
        },
        created() {
            this.fetchVehicle({id: this.id}).then(vehicle => {
                this.fetchVehicleType({id: vehicle.type})
            });
            this.fetchInsurancesBy({filters: {vehicleId: this.id}}).then(
                    ()=>{
                        utils.translateSuretyTypes(this.insurances);
                    }
            )
        },
        computed: {
            ...mapGetters([
                'vehicle',
                'vehicleType',
                'insurances',
                'hasPermissionForRoute'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["insuranceCompanyName",'suretyTypeTranslation','insuredValue','showableStartDate','cost','tax'];
                listObj.values = this.insurances;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
                'fetchVehicleType',
                'fetchInsurancesBy'
            ]),
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            },
            tdclick: function(value) {
                this.$router.push({name: this.resource.name, params: {id:value.id,contractId:value.contract}});
            }
        }
    }
</script>
<style>
    #log {
        margin-right: 10px;
    }
</style>
