<template>
    <div>
        <text-input-form-group :object="vehicleType" name="name" :text="$t('vehicleType.vehicleType')" :rules="'required'"></text-input-form-group>
        <div  v-for="tax in vehicleType.taxes">
            <text-input-form-group

                    :object="tax"
                    name="tax"
                    :text="tax.suretyTypeTr"
                    :rules="'required'"
            ></text-input-form-group>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import textInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        props: {
            vehicleType: Object
        },
        created(){
            if(this.vehicleType.taxes==undefined){
                this.vehicleType.taxes=[];
                for(let i=0;i<suretyTypes.length;i++){
                    this.vehicleType.taxes[i]={};
                    this.vehicleType.taxes[i].suretyType=suretyTypes[i].name;
                    this.vehicleType.taxes[i].suretyTypeTr=suretyTypes[i].translation();
                    this.vehicleType.taxes[i].tax=0;
                }
            }
        },
        components: {
            textInputFormGroup
        }
    }
</script>