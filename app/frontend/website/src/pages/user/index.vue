<!--
    This page shows all users in the database.
    From this page a new user can be added or an existing user can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("user.users") | capitalize}}
               <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <abstract-search-form :resource="resource" :filters="filters">
            <user-search-input :user="filters"></user-search-input>
        </abstract-search-form>
        <list-component :resource="resource" :listObject="listObject"></list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import UserSearchInput from './UserSearchInput.vue'

    export default {
        data(){
            return {
                filters: {},
                resource: resources.USER
            }
        },
        components: {
            listComponent, buttonAdd, UserSearchInput, AbstractSearchForm
        },
        created() {
            this.fetchUsers()
        },
        computed: {
            ...mapGetters([
                'users'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['firstName', 'lastName'];
                listObj.values = this.users;
                return listObj;
            },
        },
        methods: {
            ...mapActions([
                'fetchUsers',
            ])
        }
    }
</script>
