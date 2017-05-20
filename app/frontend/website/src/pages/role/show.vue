
<template>
    <div v-if="role">
        <div class="page-header">
            <h1>{{ role.name | capitalize }}
                <delete-component  :resource="resource" :id="role.id" ></delete-component>
            </h1>
        </div>
        <div class="show-role">
            <h2>{{$t('role.permissions') | capitalize}}</h2>
            <div v-for="resource in resourceNames" class="col-md-3">
                <h3>{{$t('resource.' + resource.toLowerCase()) | capitalize}}</h3>
                <div v-for="permission in permissionsMap[resource]" class="radio">
                    <label><input type="radio" :checked="permission.selected===true" disabled>
                        {{$t('action.' + permission.action)}}
                    </label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <button-back :route="{name: 'roles'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {createPermissionsMap} from './utils'
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import deleteComponent from '../../assets/general/deleteComponent.vue'
    import resources from '../../constants/resources'

    export default {
        data(){
            return {
                permissionsMap: {},
                resource:resources.ROLE
            }
        },
        props: {
            id: String
        },
        components: {
            buttonBack,deleteComponent
        },
        created(){
            this.fetchRole({id: this.id});
            let p1 = this.fetchPermissionList()
            let p2 = this.fetchPermissions({ids: {id: this.id}})
            Promise.all([p1, p2]).then(values => {
                let permissions = values[0] //A list of all permissions
                let rolePermissions = values[1] //A list of the permissions of the given role.
                //Create a map of permissions for every resource
                createPermissionsMap(this.permissionsMap, permissions, rolePermissions)
            })
        },
        computed: {
            ...mapGetters([
                'role'
            ]),
            /**
             * Return a sorted list of all of the resources in the permissionsMap
             * @returns {Array}
             */
            resourceNames(){
                let keys = []
                for (const key in this.permissionsMap) {
                    if (this.permissionsMap.hasOwnProperty(key)) {
                        keys.push(key);
                    }
                }
                return keys.sort((a,b) =>
                    this.$t('resource.' + a.toLowerCase()).localeCompare(this.$t('resource.' + b.toLowerCase())))
            }
        },
        methods: {
            ...mapActions([
                'fetchRole',
                'fetchPermissionList',
                'fetchPermissions'
            ]),
        }
    }
</script>
<style>
    .show-role label {
        cursor: default
    }
    .show-role input[type="radio"][disabled] {
        cursor: default
    }
</style>