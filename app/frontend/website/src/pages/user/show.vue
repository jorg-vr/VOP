<!--
    This page shows a certain user in detail.

    @param id: The id of the user to be shown.
-->
<template>
    <div v-if="user">
        <div class="page-header">
            <h1>{{user.lastName|capitalize}} {{user.firstName|capitalize}}
                <delete-component v-if="user" :resource="resource" :id="user.id" ></delete-component>
            </h1>
        </div>
        <div class="col-md-8">
            <h4>
            <table class="table show-table">
                <tr>
                    <td>{{$t('user.email') | capitalize }}</td>
                    <td>{{user.email}}</td>
                </tr>
                <tr v-if="user.id == activeAccount.id">
                    <td>{{$t('language.language') | capitalize}}</td>
                    <td><language-picker></language-picker></td>
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
    import deleteComponent from '../../assets/general/deleteComponent.vue'
    import FunctionIndex from '../function/index.vue'
    import resources from '../../constants/resources'
    import languagePicker from '../../assets/navbar/languagePicker.vue'

    export default {
        data(){
            return {
                resource: resources.USER
            }
        },
        components: {
            buttonBack, FunctionIndex,deleteComponent, languagePicker
        },
        props: {
            id: String
        },
        created(){
            this.fetchUser({id: this.id})
        },
        computed: {
            ...mapGetters([
                'user', 'activeAccount'
            ])
        },
        methods: {
            ...mapActions([
                'fetchUser'
            ])
        },
    }
</script>
