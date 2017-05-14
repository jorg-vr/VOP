<template>
    <abstract-form :ids="ids" :actions="actions" :object="comissions" :back="back" :resource="resource">
        <commission-form-input :object="comissions"></commission-form-input>
    </abstract-form>
</template>
<script>
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import commissionForm from '../commission/commissionForm.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonAction from '../../assets/buttons/buttonAction.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import commissionFormInput from './commissionFormInput.vue'

    export default {
        data(){
            return {
                ids: {'resource':this.loc,'resourceId':this.id},
                actions: actions.UPDATE,
                resource: resources.COMMISSION,
            }
        },
        created(){
            if(this.id){
                this.fetchCommissions({ids:this.ids});
            }
        },
        components: {
            buttonLink,
            buttonAction,
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
                'fetchCommissions',
            ])
        }
    }
</script>