<!--
This component will only be shown if the user has the correct permissions.
Place a component inside this component for permission checking.
-->

<template>
    <span v-if="hasPermission(permission)">
        <slot></slot>
    </span>
</template>

<script>
    import {mapGetters} from 'vuex'

    export default {
        props: {
            /**
             * A permission contains 2 keys: a resource and actions (a list).
             * The button will only be shown in the user has the given permission.
             * If no permission is passed, the button will always be shown.
             */
            permission: {
                type: Object,
                default(){
                    return null
                }

            }
        },
        computed: {
            ...mapGetters([
                'hasPermission'
            ])
        }
    }
</script>
