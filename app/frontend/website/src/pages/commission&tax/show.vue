<template>
    <div v-if="commissions" class="col-lg-8 col-md-9 col-sm-11">
        <div v-for="commission in commissions">
            {{commission.commission}} {{commission.suretyType}}
        </div>
        <!--<list-component :resource="resource" :objects="commissions" :visibleKeys="['suretyType','commission']"></list-component>-->
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listComponent.vue"
    export default {
        data(){
            return {
                resource: resources.COMMISSION
            }
        },
        components: {
            listComponent
        },
        created() {
            this.setLoading({loading: true})
            this.fetchCommissions({}).then(() => {
                this.setLoading({loading: false })
            }).catch(response=>{
                console.log(response)
            })
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

//ids:{'resource':'vehicles/types','id':'274776102288499320837812636171303961393'}