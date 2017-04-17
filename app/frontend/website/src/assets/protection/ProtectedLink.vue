<!--
A router link which will only be shown if the user has correct permissions.

@param permission: An object containing a resource and actions field which the user needs to view the component
@param to: Destination of the link
-->
<template>
    <protected-element :permission="permission">
        <router-link :to="to">
            <slot></slot>
        </router-link>
    </protected-element>
</template>
<script>
    import ProtectedElement from './ProtectedElement'

    export default {
        components: {
                'protected-element': ProtectedElement
        },
        props: {
            permission: Object,
            to: Object
        }
    }
</script>