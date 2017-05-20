/**
 * Created by Jarre on 18/05/2017.
 */

import Vue from 'vue'

/**
 * Create a map of permissions with as key the name of the resource and as value a list of permissions for the resource.
 * The map will contain all possible permissions and will add a selected property indicating if the permission is part
 * of the permissions argument.
 * @param permissionsMap An empty object which serve as the permissions map
 * @param permissionList A list of all possible permissions
 * @param permissions A subset of the list of permissions
 */
export const createPermissionsMap = function(permissionsMap, permissionList, permissions) {
    for(let i=0; i<permissionList.length; i++){ //iterate over every possible permissions
        let permission = permissionList[i]
        let entry = permissionsMap[permission.resource]
        if(!entry){
            entry = [] //All permissions with the same resource will belong to the same list.
        }
        entry.push(permission)
        permission.selected = false
        Vue.set(permission, 'selected', false)
        Vue.set(permissionsMap, permission.resource, entry)
    }
    //Sort the permissions by their action name.
    for (const key in permissionsMap) {
        if (permissionsMap.hasOwnProperty(key)) {
            let permissions = permissionsMap[key]
            permissionsMap[key] = permissions.sort((a,b) =>
                Vue.t('action.' + a.action).localeCompare(Vue.t('action.' + b.action)))
        }
    }
    //Mark the permissions of the role as selected in the complete list.
    if(permissions){
        for(let i=0; i<permissions.length; i++){
            let permission = permissions[i]
            let permission2 = permissionsMap[permission.resource].filter(permission2 => permission2.id === permission.id)[0]
            permission2.selected = true
        }
    }
}