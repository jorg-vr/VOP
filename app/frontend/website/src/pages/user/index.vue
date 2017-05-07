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
        <user-search-bar @search="updateUsers" @advancedSearch="updateUsersAdvanced"></user-search-bar>
        <!-- Render an info-pane for every user. Once all the data is loaded, the table will be shown.-->
        <list-component :resource="resource" :listObject="listObject">
        </list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import userSearchBar from '../../assets/search/types/userSearchBar.vue'

    export default {
        data(){
            return {
                resource: resources.USER
            }
        },
        components: {
            listComponent, buttonAdd, userSearchBar
        },
        created() {
            this.fetchUsers().then(users => {
                this.setFilteredUsers(users)
            })
        },
        computed: {
            ...mapGetters([
                'users',
                'filteredUsers',
                'getUsersByAll',
                'getUsersByAllAdvanced'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['firstName', 'lastName'];
                listObj.values = this.filteredUsers;
                return listObj;
            },
        },
        methods: {
            ...mapActions([
                'fetchUsers',
                'deleteUser'
            ]),
            ...mapMutations([
                'setFilteredUsers'
            ]),
            updateUsers(value){
                if(value!==''){
                    this.setFilteredUsers(this.getUsersByAll(value))
                }
                else {
                    this.setFilteredUsers(this.users)
                }
            },
            updateUsersAdvanced(filterUser){
                this.setFilteredUsers(this.getUsersByAllAdvanced(filterUser))
            }
        }
    }
</script>
