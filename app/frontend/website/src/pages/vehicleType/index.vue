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
                <button-remove :resource="resource" @click="tdshowModal(vehicleType.id)"></button-remove>
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
                        <th >
                            {{$t('vehicleType.tax')| capitalize }}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="suretyType in vehicleType.merge" class="list-tr">
                        <td  >
                            {{$t('suretyTypes.'+suretyType.name)}}
                        </td>
                        <td  >
                            {{(suretyType.commission*100).toFixed(2)}}%
                        </td>
                        <td  >
                            {{(suretyType.tax*100).toFixed(2)}}%
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Confirmation Modal -->
        <confirm-modal v-show="showModal"
                       @cancelModal="showModal=false"
                       @confirmModal="confirmAction()"
                       @close="showModal=false "
                       :modalHeaderTitle=" $t('modal.titleConfirm') | capitalize"
                       :modalBodyText="$t('modal.textConfirm') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize ">
        </confirm-modal>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import * as locations from '../../constants/locations'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import confirmModal from '../../assets/general/modal.vue'

    export default {
        data(){
            return {
                resource: resources.VEHICLE_TYPE,
                show:false,
                showModal:false,
                selectedvalue:""
            }
        },
        components: {
            listComponent, buttonAdd,buttonEdit,buttonRemove,confirmModal
        },
        created(){
            this.refreshVehicleTypes;
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
                'fetchCommissions',
                'deleteVehicleType'
            ]),
            mergeCommissionsAndTaxes(commissions, taxes){
                let merge={};
                for(let i=0;i<commissions.length;i++){
                    merge[commissions[i].suretyType]={};
                    merge[commissions[i].suretyType].commission=commissions[i].commission;
                    merge[commissions[i].suretyType].name=commissions[i].suretyType;
                }
                for(let i=0;i<taxes.length;i++){
                    if(merge[taxes[i].suretyType]==undefined){
                        merge[taxes[i].suretyType]={};
                    }
                    merge[taxes[i].suretyType].tax=taxes[i].tax;
                    merge[taxes[i].suretyType].name=taxes[i].suretyType;
                }
                return merge;
            },
            refreshVehicleTypes(){
                this.fetchVehicleTypes().then(
                        ()=>{
                            let p=[]
                            for(let i=0;i<this.vehicleTypes.length;i++) {
                                p[i]=this.fetchCommissions({ids: {'resource': locations.VEHICLE_TYPE, 'resourceId': this.vehicleTypes[i].id}})
                            }
                            Promise.all(p).then(
                                    com=>{
                                        for(let i=0;i<this.vehicleTypes.length;i++) {
                                            this.vehicleTypes[i].merge = this.mergeCommissionsAndTaxes(com[i], this.vehicleTypes[i].taxes);
                                        }
                                        this.show = true;
                                    }
                            )

                        }
                );
            },
            confirmAction: function(){
                // hide modal
                this.showModal=false
                // remove object
                this.deleteVehicleType({id: this.selectedvalue, ids: this.ids});
                this.refreshVehicleTypes();
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            }
        }
    }
</script>
