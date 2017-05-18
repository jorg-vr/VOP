<!--
    This page shows all users in the database.
    From this page a new user can be added or an existing user can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("role.roles") | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <list-component :resource="resource" :listObject="listObject"></list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return {
                filters: {},
                resource: resources.ROLE
            }
        },
        components: {
            listComponent, buttonAdd
        },
        created() {
            this.fetchRoles()
        },
        computed: {
            ...mapGetters([
                'roles'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['name'];
                listObj.values = this.roles;
                return listObj;
            },
        },
        methods: {
            ...mapActions([
                'fetchRoles',
            ])
        }
    }
</script>
