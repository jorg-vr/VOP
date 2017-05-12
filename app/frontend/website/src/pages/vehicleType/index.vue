<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("vehicleType.vehicleType") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <div v-for="vehicleType in vehicleTypes">
            <h3>
                {{vehicleType.name | capitalize }}
                <button-edit :resource="resource" :params="{id: vehicleType.id}"></button-edit>
                <button-remove :resource="resource"></button-remove>
            </h3>
            <div v-if="show" >
                <table class="table-hover table">
                    <thead>
                    <tr>
                        <th >
                            {{$t('commission.suretyType')| capitalize }}
                        </th>
                        <th >
                            {{$t('commission.commission')| capitalize }}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="commission in vehicleType.commissions" class="list-tr">
                        <td  >
                            {{$t('suretyTypes.'+commission.suretyType)}}
                        </td>
                        <td  >
                            {{commission.commission*100}}%
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import * as locations from '../../constants/locations'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                show:false
            }
        },
        components: {
            listComponent, buttonAdd,buttonEdit,buttonRemove
        },
        created(){
            this.fetchVehicleTypes().then(
                    ()=>{
                        let p=[]
                        for(let i=0;i<this.vehicleTypes.length;i++) {
                            p[i]=this.fetchCommissions({ids: {'resource': locations.VEHICLE_TYPE, 'resourceId': this.vehicleTypes[i].id}})
                        }
                        Promise.all(p).then(
                                com=>{
                                    for(let i=0;i<this.vehicleTypes.length;i++) {
                                        this.vehicleTypes[i].commissions = com[i];
                                    }
                                    this.show = true;
                                }
                        )

                    }
            );
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
                'fetchVehicleTypes',
                'fetchCommissions'
            ])
        }
    }
</script>
