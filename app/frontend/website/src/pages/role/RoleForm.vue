<!--
All of the fields for user input for the user form

@param user: This object will be configured with the input of this component.
-->
<template>
    <div id="content-wrapper" v-if="role ">
        <div class="page-header">
            <h1>{{ submitText }}</h1>
        </div>
        <part :back="back" :actions="actions" :resource="resource" :customSubmit="submit">
            <text-input-form-group :object="role" name="name" :text="$t('role.name')" :rules="'required'"></text-input-form-group>
            <h2>{{$t('role.permissions') | capitalize}}</h2>
            <div v-for="resource in resourceNames" class="col-md-3">
                <h3>{{$t('resource.' + resource.toLowerCase()) | capitalize}}</h3>
                <div v-for="permission in permissionsMap[resource]" class="radio">
                    <label><input :id="permission.id" type="radio" :checked="permission.selected===true" @click="updatePermission(permission)">
                        {{$t('action.' + permission.action)}}
                    </label>
                </div>
            </div>
        </part>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import {mapGetters, mapActions} from 'vuex'
    import errors from '../../assets/form/AbstractFormErrors.vue'
    import part from '../../assets/form/AbstractFormPart.vue'
    import {getResourceActionText} from '../../utils/utils'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import {createPermissionsMap} from './utils'

    export default {
        data(){
            return {
                permissionsMap: {},
                resource: resources.ROLE,
                submitText:  getResourceActionText(resources.ROLE.name, this.actions.name)
            }
        },
        props: {
            id: String, //Id of the role
            role: Object,
            actions: Object,
            back: Object
        },
        components: {
            TextInputFormGroup, errors, part
        },
        created(){
            let p2
            if(this.actions.name===actions.UPDATE.name){
                p2 = this.fetchPermissions({ids: {id: this.id}})
            }
            let p1 = this.fetchPermissionList()
            Promise.all([p1, p2]).then(values => {
                let permissions = values[0] //A list of all permissions
                let rolePermissions = values[1] //A list of the permissions of the given role.
                //Create a map of permissions for every resource
                createPermissionsMap(this.permissionsMap, permissions, rolePermissions)
            })
        },
        computed: {
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
                'updatePermissions',
                'fetchPermissions',
                'fetchPermissionList'
            ]),
            updatePermission(permission){
                permission.selected = !permission.selected
                document.getElementById(permission.id).checked = permission.selected
            },
            submit(){
                //Make a list of permissions for the role
                let permissions = []
                for (const key in this.permissionsMap) {
                    if (this.permissionsMap.hasOwnProperty(key)) {
                        let resourcePermissions = this.permissionsMap[key]
                        for(let i=0; i < resourcePermissions.length; i++){
                            let permission = resourcePermissions[i]
                            if(permission.selected){
                                permissions.push(permission.id)
                            }
                        }
                    }
                }
                return new Promise((resolve, reject) => {
                    this.$store.dispatch(this.actions.name + this.resource.name.capitalize(), {resource: this.role})
                        .then(role => {
                                this.updatePermissions({roleId: role.id, permissions}).then(() => {
                                    resolve()
                                }, () => {
                                    reject()
                                    this.$router.push({name: this.back.name, params: this.back.params})
                                })
                            },
                            response => {
                                console.log(response)
                                reject()
                            })
                })
            }
        }
    }
</script>