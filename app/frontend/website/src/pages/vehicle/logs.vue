<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("log.log") | capitalize}} {{$t("vehicle.vehicle")}} {{vehicle.licensePlate}}
            </h1>
        </div>
        <!-- Render an info-pane for every client. Once all the data is loaded, the table will be shown.-->
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
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{value.showableDateTime}}
                </td>
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{$t('log.' + value.action.toLowerCase()) | capitalize}}
                </td>
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{$t('resource.' + value.resource.toLowerCase()) | capitalize}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../constants/resources'

    export default {
        data(){
            return {
                resource: resources.LOG,
                vehicle: {},
                logs: [],
            }
        },
        props: {
            id: String
        },

        created(){
            this.fetchVehicle({id: this.id}).then(vehicle => {
                this.vehicle = vehicle
            })
            this.fetchVehicleLogs({id: this.id}).then(logs => {
                this.logs = logs
            })
        },
        computed: {
            listObject(){
                return {
                    headers: ["showableDateTime", "action", "resource"],
                    values: this.logs
                }
            }
        },
        methods: {
            ...mapActions([
                'fetchVehicle',
                'fetchVehicleLogs'
            ]),
            tdclick: function(id, value) {
                //TODO: remove value
                this.$router.push({name: 'vehicle_log', params: {vehicleId: this.vehicle.id, id: id, entry: value}});
            }
        }
    }
</script>