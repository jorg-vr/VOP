<template>
    <div v-if="commissions" >
        <div v-if="show&&commissions.length>0">
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
        <h4 v-else-if="show"> {{$t('commission.none')| capitalize }} </h4>
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
                commissions: [],
                show:false
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
            console.log(this.loc)
            console.log(this.id)
            this.fetchCommissions({ids:{'resource':this.loc,'resourceId':this.id}}).then(commissions=>{
                this.commissions=commissions;
                this.commissions.sort((a,b)=>a.suretyType>b.suretyType);
                this.show=true;
            });
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
