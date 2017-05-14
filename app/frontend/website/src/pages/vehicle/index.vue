<!--
    This page shows all vehicles in the database.
    From this page a new vehicle can be added or an existing vehicle can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>{{$t("vehicle.vehicles") | capitalize }}</h1>
        </div>
        <abstract-search-form :resource="resource" :filters="filters">
            <vehicle-search-input :vehicle="filters"></vehicle-search-input>
        </abstract-search-form>
        <!-- Render an info-pane for every vehicle. Once all the data is loaded, the table will be shown.-->
        <list-component v-if="vehicles.length > 0" :resource="resource" :listObject="listObject"></list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import VehicleSearchInput from './VehicleSearchInput.vue'

    export default {
        data(){
            return {
                filters: {},
                resource: resources.VEHICLE
            }
        },
        components: {
            listComponent, buttonAdd, AbstractSearchForm, VehicleSearchInput
        },
        created() {
            this.clearVehicles()
        },
        mounted(){
            $('.collapse').collapse("show")
        },
        computed: {
            ...mapGetters([
                'vehicles'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['brand','model', 'licensePlate'];
                listObj.values = this.vehicles;
                return listObj;
            }
        },
        methods: {
            ...mapMutations([
                'clearVehicles'
            ])
        }
    }
</script>
