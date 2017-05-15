<template>
    <abstract-form :actions="actions" :object="commissions" :back="back" :resource="resource" :ids="ids">
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
                show:false
            }
        },
        created(){
            if(this.id){
                this.fetchCommissions({ids: this.ids}).then(()=>{
                    console.log(this.commissions);
                    if(this.commissions==false){
                        for(let i=0;i<suretyTypes.length;i++){
                            this.commissions[i]={
                                suretyType: suretyTypes[i].name,
                                commission: 0
                            }
                        }
                    }
                    this.show=true;
                })
            }
        },
        components: {
            abstractForm,
            commissionFormInput
        },
        props: {
            back: Object,
            id: String,
            loc:String
        },
        computed: {
            ...mapGetters([
                'commissions'
            ])
        },
        methods: {
            ...mapActions([
                'fetchCommissions'
            ])
        }
    }
</script>