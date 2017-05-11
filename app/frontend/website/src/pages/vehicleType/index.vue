<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("vehicleType.vehicleType") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <list-component v-if="vehicleTypes.length > 0" :resource="resource" :listObject="listObject"></list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE
            }
        },
        components: {
            listComponent, buttonAdd
        },
        created(){
            this.fetchVehicleTypes();
        },
        computed: {
            ...mapGetters([
                'vehicleTypes'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['name'];
                listObj.values = this.vehicleTypes;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchVehicleTypes'
            ])
        }
    }
</script>
