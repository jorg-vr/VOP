<template>
    <div v-if="commissions" class="col-lg-8 col-md-9 col-sm-11">
        <table class="table-hover table">
            <thead>
            <tr>
                <th >
                    {{$t(commission.suretyType).capitalize()}}
                </th>
                <th >
                    {{$t(commission.commission).capitalize()}}
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="commission in commissions" class="list-tr">
                <td  class="clickable-td" >
                    {{commission.suretyType}}
                </td>
                <td  class="clickable-td">
                    {{commission.commission}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>
<style>
    .stretch {
        width: 1%;
        white-space: nowrap;
    }

    .clickable-td {
        cursor:pointer;
    }
    tr.list-tr {
        border-bottom: 1px solid #e6e6e6;
    }
</style>
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
            this.fetchCommissions({}).then(() => {
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