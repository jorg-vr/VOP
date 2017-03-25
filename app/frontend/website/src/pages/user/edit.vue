<!--
    This page is used to edit a certain user.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("user.user") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <user-form :oldUser=user :submit="updateUser"></user-form>
    </div>
</template>
<script>
    import UserForm from '../../assets/form/types/userForm.vue'
    import {mapGetters, mapActions} from 'vuex'


    export default {
        components: {
            UserForm
        },
        created(){
            this.fetchUser({id: this.$route.params.id})
        },
        computed: {
            ...mapGetters([
                'user'
            ])
        },
        methods: {
            ...mapActions([
                'fetchUser',
                'updateUser'
            ])
        }
    }
</script>

