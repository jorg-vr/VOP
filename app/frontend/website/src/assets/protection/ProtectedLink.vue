<template>
    <permission-component :permission="permission">
        <router-link :to="to">
            <slot></slot>
        </router-link>
    </permission-component>
</template>
<script>
    import PermissionComponent from './ProtectedElement.vue'
    
    export default {
        components: {
            PermissionComponent
        },
        props: {
            permission: Object,
            to: Object
        }
    }
</script>