<template>
    <abstract-form :actions="actions" :object="comissions" :back="back" :resource="resource">
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
                actions: actions.UPDATE,
                resource: resources.COMMISSION,
            }
        },
        created(){
            if(this.id){
                this.fetchCommissions({ids:{'resource':this.loc,'resourceId':this.id}});
            }
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    this.submit()
                }
            })
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
                'commissions','error'
            ])
        },
        methods: {
            ...mapActions([
                'fetchCommissions',
                'updateCommission'
            ]),
            submit(){
                this.commissions.id='';
                this.updateCommission({resource: this.commissions, ids: {'resource':this.loc,'resourceId':this.id}}).then(() => {
                    this.$router.push(this.back)
                })
            }

        }
    }
</script>