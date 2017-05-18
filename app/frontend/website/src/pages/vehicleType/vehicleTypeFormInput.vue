<template>
    <div>
        <text-input-form-group :object="vehicleType" name="name" :text="$t('vehicleType.vehicleType')" :rules="'required'"></text-input-form-group>
        <h2>{{$t('vehicleType.taxes') | capitalize}}</h2>
        <div v-for="tax in vehicleType.taxes">
            <percent-input-form-group
                    :object="tax"
                    name="tax"
                    :text="$t('suretyTypes.'+tax.suretyType)"
                    :rules="'required'"
            ></percent-input-form-group>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import percentInputFormGroup from '../../assets/form/FormGroups/PercentInputformGroup.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        props: {
            vehicleType: Object
        },
        created(){
            if(this.vehicleType.taxes===undefined){
                this.vehicleType.taxes=[];
                for(let i=0;i<suretyTypes.length;i++){
                    this.vehicleType.taxes[i]={};
                    this.vehicleType.taxes[i].suretyType=suretyTypes[i].name;
                    this.vehicleType.taxes[i].tax=0;
                }
            }
        },
        components: {
            percentInputFormGroup
        }
    }
</script>