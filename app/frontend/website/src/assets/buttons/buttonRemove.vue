<template>
    <permission-component :permission="permission">
        <button-action @click="emitClicked" buttonClass="btn btn-md btn-danger" buttonId="remove">
            <i aria-hidden="true" class="fa fa-trash"></i>
        </button-action>
    </permission-component>

</template>
<script>
    import * as actions from '../../constants/actions'
    import buttonAction from './buttonAction.vue'
    import PermissionComponent from '../general/PermissionComponent.vue'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resourceName.toUpperCase(),
                    actions: actions.DELETE
                }
            }
        },
        components: {
            buttonAction, PermissionComponent
        },
        props: {
            resourceName: String, //Name of the resource
            buttonClass: String
        }
    }
</script>