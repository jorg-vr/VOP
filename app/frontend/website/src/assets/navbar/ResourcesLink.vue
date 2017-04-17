<!--
    This is a protected link which is only shown if the user has READ access for the given resource.
    The name of the resource will be shown in the link.

    @param resource: The resource to which this link navigates.
-->

<template>
    <protected-link :to="{name: resource.name.plural()}" :permission="permission">
        {{ $t(resource.name + '.' + resource.name.plural()) | capitalize}}
    </protected-link>
</template>
<script>
    import ProtectedLink from '../protection/ProtectedLink.vue'
    import actions from '../../constants/actions'
    import {PagePermissions} from '../../constants/PagePermissions'

    export default {
        data(){
            return {
                permission: PagePermissions[actions.READ_ALL.path(this.resource.name)]
            }
        },
        components: {
            ProtectedLink
        },
        props: {
            resource: Object
        }
    }
</script>