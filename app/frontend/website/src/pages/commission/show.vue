<template>
    <div v-if="commissions" >
        <div v-if="commissions.length>0">
            <h2>
                {{$t('commission.commissions')}}
            </h2>
            <table class="table-hover table">
                <thead>
                <tr>
                    <th >
                        {{$t('commission.suretyType')| capitalize }}
                    </th>
                    <th >
                        {{$t('commission.commission')| capitalize }}
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="commission in commissions" class="list-tr">
                    <td  >
                        {{$t('suretyTypes.'+commission.suretyType)}}
                    </td>
                    <td  >
                        {{commission.commission*100}}%
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    export default {
        data(){
            return {
                resource: resources.COMMISSION,
                commission: {}
            }
        },
        props:{
            id:String,
            loc:String
        },
        components: {
            listComponent
        },
        created() {
            this.fetchCommissions({ids:{'resource':this.loc,'resourceId':this.id}});
        },
        computed: {
            ...mapGetters([
                'commissions'
            ])
        },
        methods: {
            ...mapActions([
                'fetchCommissions'
            ]),
            ...mapMutations([
                'setLoading'
            ])
        }
    }
</script>
