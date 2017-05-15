<template>
    <abstract-form :actions="actions" :object="commissions" :back="back" :resource="resource" :ids="ids">
        <commission-form-input :object="commissions"></commission-form-input>
    </abstract-form>

</template>
<script>
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import commissionFormInput from '../commission/commissionFormInput.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import abstractForm from '../../assets/form/AbstractFormPart.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.COMMISSION,
                ids: {'resource':this.loc,'resourceId':this.id}
            }
        },
        created(){
            let com=[];

            this.setCommissions()
            if(this.id){
                this.fetchCommissions({ids: this.ids});
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