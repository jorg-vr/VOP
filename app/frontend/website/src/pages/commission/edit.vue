<template>
    <div>
        <h3>{{$t('commission.commissions')|capitalize}}</h3>
        <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
            <commissionForm :commissions="commissions"></commissionForm>
            <div class="row">
                <button-link :route="back" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                    {{ $t('common.cancel') | capitalize }}
                </button-link>
                <button-action @click="submit()" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                    {{$t('common.update')|capitalize }}
                </button-action>
            </div>
        </form>
    </div>
</template>
<script>
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import commissionForm from '../commission/commissionForm.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import buttonAction from '../../assets/buttons/buttonAction.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'

    export default {
        data(){
            return {
                resource: resources.COMMISSION,
                actions: actions.UPDATE
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
            commissionForm
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