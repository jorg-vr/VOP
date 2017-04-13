<!--
    This page shows all users in the database.
    From this page a new user can be added or an existing user can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("user.user") | capitalize}}
               <button-add resourceName="user"></button-add>
            </h1>
        </div>
        <user-search-bar @search="updateUsers" @advancedSearch="updateUsersAdvanced"></user-search-bar>
        <!-- Render an info-pane for every user. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="user in filteredUsers"
                        v-if="user"
                        :object="user"
                        :visibleKeys="new Array('firstName', 'lastName')"
                        :remove="deleteUser"
                        edit="edit_user"
                        show="user"
                        :key="user.id">
        </list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import userSearchBar from '../../assets/search/types/userSearchBar.vue'

    export default {
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
            ])
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
