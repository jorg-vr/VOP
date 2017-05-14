<template>
    <div>
        <div class="page-header">
            <h1>{{ submitText }}</h1>
        </div>
        <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
            <form-input :vehicleType="vehicleType"></form-input>
            <h2>{{$t('commission.commissions')}}</h2>
            <commissionForm :commissions="commissions"></commissionForm>
            <div class="row">
                <button-link :route="back" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                    {{ $t('common.cancel') | capitalize }}
                </button-link>
                <button-action @click="submit()" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                    {{ submitText }}
                </button-action>
            </div>
        </form>
    </div>
</template>
<script>
    import {mapActions,mapGetters} from 'vuex'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './vehicleTypeForm.vue'
    import commissionForm from '../commission/commissionForm.vue'
    import * as locations from '../../constants/locations'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonAction from '../../assets/buttons/buttonAction.vue'
    import {getResourceActionText} from '../../utils/utils'

    export default {
        data(){
            return {
                submitText:  getResourceActionText(resources.VEHICLE_TYPE.name, actions.CREATE.name),
                back:{name:resources.VEHICLE_TYPE.name.plural()},
                loc:locations.VEHICLE_TYPE,
                show:false,
                vehicleType:{},
                commissions:[]
            }
        },
        created(){
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    this.submit()
                }
            })
        },
        components: {
            buttonLink,
            buttonAction,formInput,commissionForm
        },
        props: {
            id: String
        },
        computed: {
            ...mapGetters(['error'
            ])
        },
        methods: {
            ...mapActions([
                'createVehicleType',
                'updateCommission'
            ]),
            submit(){
                this.commissions.id='';
                this.createVehicleType({resource:this.vehicleType}).then(
                        vehicleType =>{
                            console.log(vehicleType)
                            this.updateCommission({resource: this.commissions, ids: {'resource':this.loc,'resourceId':vehicleType.id}}).then(() => {
                                this.$router.push(this.back)
                            })
                        }
                )
            }

        }
    }
</script>