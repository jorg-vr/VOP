<!--
    This is a protected link which is only shown if the user has READ access for the given resource.
    The name of the resource will be shown in the link.
-->

<template>
    <protected-link :to="{name: resourceName + 's'}" :permission="permission">{{ $t(resourceName + '.' + resourceName + 's') | capitalize}}</protected-link>
</template>
<script>
    import ProtectedLink from '../protection/ProtectedLink.vue'
    import * as actions from '../../constants/actions'

    export default {
        data(){
            return {
                permission: {
                    resource: this.resourceName.toUpperCase(),
                    actions: actions.READ
                }
            }
        },
        components: {
            ProtectedLink
        },
        props: {
            resourceName: String,
        }
    }
</script>