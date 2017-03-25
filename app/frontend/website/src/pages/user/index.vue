<!--
    This page shows all users in the database.
    From this page a new user can be added or an existing user can be edited or removed.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("user.users") | capitalize }}</h1>
        </div>
        <!-- Render an info-pane for every user. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="user in users"
                        v-if="user"
                        :object="user"
                        :visibleKeys="new Array('firstName', 'lastName')"
                        :remove="deleteUser"
                        edit="edit_user"
                        show="user"
                        :key="user.id">
        </list-component>
        <button-add :route="{name: 'new_user'}"></button-add>
    </div>
</template>
<script>
    import { mapGetters, mapActions } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        components: {
            listComponent, buttonAdd
        },
        created() {
            this.fetchUsers()
        },
        computed: {
            ...mapGetters([
                'users'
            ])
        },
        methods: {
            ...mapActions([
                'fetchUsers',
                'deleteUser',
            ])
        }
    }
</script>

<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
    }
</style>