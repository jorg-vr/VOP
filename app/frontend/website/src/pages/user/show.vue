<!--
    This page shows a certain user in detail.

    @param id: The id of the user to be shown.
-->
<template>
    <div v-if="user">
        <div class="page-header">
            <h1>{{user.lastName|capitalize}} {{user.firstName|capitalize}}
                <button-edit v-if="user" :resource="resource" :params="{id: user.id}" ></button-edit>
            </h1>
        </div>
        <div class="col-md-8">
            <h4>
            <table class="table show-table">
                <tr>
                    <td>{{$t('user.email') | capitalize }}</td>
                    <td>{{user.email}}</td>
                </tr>
            </table>
            </h4>
            <function-index :userId="id"></function-index>
            <button-back :route="{name: 'users'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import FunctionIndex from '../function/index.vue'
    import resources from '../../constants/resources'

    export default {
        data(){
            return {
                resource: resources.USER
            }
        },
        components: {
            buttonBack, FunctionIndex,buttonEdit
        },
        props: {
            id: String
        },
        created(){
            this.fetchUser({id: this.id})
        },
        computed: {
            ...mapGetters([
                'user'
            ])
        },
        methods: {
            ...mapActions([
                'fetchUser'
            ])
        },
    }
</script>
