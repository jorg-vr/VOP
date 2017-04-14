<!--
    This page shows a certain user in detail. 
-->
<template>
    <div v-if="user">
        <div class="page-header">
            <h1>{{$t("user.user") | capitalize }}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table">
                <tr>
                    <td>{{$t('user.firstName') | capitalize }}</td>
                    <td>{{user.firstName}}</td>
                </tr>
                <tr>
                    <td>{{$t('user.lastName') | capitalize }}</td>
                    <td>{{user.lastName}}</td>
                </tr>
                <tr>
                    <td>{{$t('user.email') | capitalize }}</td>
                    <td>{{user.email}}</td>
                </tr>
            </table>
            <button-back :route="{name: 'users'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    export default {
        components: {
            buttonBack
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
