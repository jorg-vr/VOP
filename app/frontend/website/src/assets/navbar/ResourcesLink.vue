<!--
    This is a protected link which is only shown if the user has READ access for the given resource.
    The name of the resource will be shown in the link.
-->

<template>
    <protected-link :to="{name: resource.name.plural()}" :permission="permission">
        {{ $t(resource.name + '.' + resource.name.plural()) | capitalize}}
    </protected-link>
</template>
<script>
    import ProtectedLink from '../protection/ProtectedLink.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                permission: {
                    resource: this.resource,
                    actions: actions.READ_ALL
                }
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