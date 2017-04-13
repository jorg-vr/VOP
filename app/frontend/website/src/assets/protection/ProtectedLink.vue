<template>
    <permission-component :permission="permission">
        <router-link :to="to"></router-link>
    </permission-component>
</template>
<script>
    import PermissionComponent from './ProtectedElement.vue'
    import * as actions from '../../constants/actions'

    /**
     * At the moment the link only supports READ permissions.
     * This can be expanded if the need arises.
     */
    export default {
        data(){
            return {
                permission: {
                    resource: this.resource,
                    actions: actions.READ
                }
            }
        },
        components: {
            PermissionComponent
        },
        props: {
            resource: String,
            to: Object
        }
    }
</script>