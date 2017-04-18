<!--
A button used for removing an object. The remove function has to be bound to the click event of this button.
The button will only be shown if the user has the correct permissions.

@param resource: The resource of the object to be deleted.
@param buttonClass (optional): The HTML class for this button
-->

<template>
    <protected-element :permission="permission">
        <button-action @click="$emit('click')" buttonClass="btn btn-md btn-danger" buttonId="remove">
            <i aria-hidden="true" class="fa fa-trash"></i>
        </button-action>
    </protected-element>
</template>
<script>
    import actions from '../../constants/actions'
    import buttonAction from './buttonAction.vue'
    import ProtectedElement from '../protection/ProtectedElement.js'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resource,
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