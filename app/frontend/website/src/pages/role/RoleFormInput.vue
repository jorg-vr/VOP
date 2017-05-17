<!--
All of the fields for user input for the user form

@param user: This object will be configured with the input of this component.
-->
<template>
    <div>
        <text-input-form-group :object="role" name="name" :text="$t('role.name')" :rules="'required'"></text-input-form-group>
        <div v-for="resource in resources">
            <h2>{{$t('resource.' + resource.toLowerCase()) | capitalize}}</h2>
            <div v-for="permission in permissionsMap[resource]" class="radio">
                <label><input type="radio" :checked="permission.selected===true" @click="updatePermission(permission)">
                    {{$t('action.' + permission.action)}}
                </label>
            </div>
        </div>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        data(){
            return {
                permissionsMap: {}
            }
        },
        props: {
            id: String, //Id of the role
            role: Object,
        },
        components: {
            TextInputFormGroup
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        created(){
            let p1 = this.fetchPermissionList()
            let p2 = this.fetchPermissions({ids: {id: this.id}})
            Promise.all([p1, p2]).then(values => {
                let permissions = values[0] //A list of all permissions
                let rolePermissions = values[1] //A list of the permissions of the given role.
                //Create a map of permissions for every resource
                for(let i=0; i<permissions.length; i++){ //iterate over every possible permissions
                    let permission = permissions[i]
                    let entry = this.permissionsMap[permission.resource]
                    if(!entry){
                        entry = [] //All permissions with the same resource will belong to the same list.
                    }
                    entry.push(permission)
                    permission.selected = false
                    this.$set(permission, 'selected', false)
                    this.$set(this.permissionsMap, permission.resource, entry)
                }
                //Sort the permissions by their action name.
                for (const key in this.permissionsMap) {
                    if (this.permissionsMap.hasOwnProperty(key)) {
                        let permissions = this.permissionsMap[key]
                        this.permissionsMap[key] = permissions.sort((a,b) =>
                            this.$t('action.' + a.action).localeCompare(this.$t('action.' + b.action)))
                    }
                }
                //Mark the permissions of the role as selected in the complete list.
                for(let i=0; i<rolePermissions.length; i++){
                    let permission = rolePermissions[i]
                    let permission2 = this.permissionsMap[permission.resource].filter(permission2 => permission2.id === permission.id)[0]
                    permission2.selected = true
                }
            })
        },
        computed: {
            /**
             * Return a sorted list of all of the resources in the permissionsMap
             * @returns {Array}
             */
            resources(){
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
                'fetchPermissions',
                'fetchPermissionList'
            ]),
            updatePermission(permission){
                permission.selected = !permission.selected
            }
        }
    }
</script>