<template>
    <protected-element :permission="permission">
        <router-link :to="to">
            <slot></slot>
        </router-link>
    </protected-element>
</template>
<script>
    import ProtectedElement from './ProtectedElement.vue'

    export default {
        components: {
                ProtectedElement
        },
        props: {
            permission: Object,
            to: Object
        }
    }
</script>