<template>
    <abstract-form :actions="actions" :object="commissions"  :resource="resource" :customSubmit="submit">
        <commission-form-input :commissions="commissions" v-if="show"></commission-form-input>
    </abstract-form>

</template>
<script>
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import commissionFormInput from '../commission/commissionFormInput.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import abstractForm from '../../assets/form/AbstractFormPart.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.COMMISSION,
                ids: {'resource':this.loc,'resourceId':this.id},
                show:false,
                commissions:[]
            }
        },
        created(){
            if(this.id){
                this.fetchCommissions({ids: this.ids}).then(commissions=>{
                    this.commissions=commissions;
                    if(this.commissions==false){
                        for(let i=0;i<suretyTypes.length;i++){
                            this.commissions[i]={
                                suretyType: suretyTypes[i].name,
                                commission: 0
                            }
                        }
                    }
                    this.commissions.sort((a,b)=>a.suretyType>b.suretyType);

                    this.show=true;
                })
            }
        },
        components: {
            abstractForm,
            commissionFormInput
        },
        props: {
            id: String,
            loc:String,
            back:Function
        },
        methods: {
            ...mapActions([
                'fetchCommissions',
                'updateCommission'
            ]),
            submit(){
                console.log("submit");
                this.updateCommission({resource: this.commissions, ids: this.ids});
                this.back();
            }
        }
    }
</script>