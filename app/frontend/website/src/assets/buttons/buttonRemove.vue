<template>
    <protected-element :permission="permission">
        <button-action @click="$emit('click')" buttonClass="btn btn-md btn-danger" buttonId="remove">
            <i aria-hidden="true" class="fa fa-trash"></i>
        </button-action>
    </protected-element>

</template>
<script>
    import * as actions from '../../constants/actions'
    import buttonAction from './buttonAction.vue'
    import ProtectedElement from '../protection/ProtectedElement.js'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resource.value,
                    actions: actions.DELETE
                }
            }
        },
        components: {
            buttonAction, ProtectedElement
        },
        props: {
            resource: Object,
            buttonClass: String
        }
    }
</script>