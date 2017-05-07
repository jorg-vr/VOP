<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("client.client") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div><!-- Render an info-pane for every vehicleType. Once all the data is loaded, the table will be shown.-->
        <list-component :resource="resource" :objects="vehicleTypes" :visibleKeys="['name']"></list-component>
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
            this.fetchVehicleTypes().then(() => {
                this.setLoading({loading: false });
            });
        },
        computed: {
            ...mapGetters([
                'vehicleTypes'
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicleTypes'
            ])
        }
    }
</script>
