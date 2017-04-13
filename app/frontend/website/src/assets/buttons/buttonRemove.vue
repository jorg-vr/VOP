<template>
    <permission-component :permission="permission">
        <button-action @click="$emit('click')" buttonClass="btn btn-md btn-danger" buttonId="remove">
            <i aria-hidden="true" class="fa fa-trash"></i>
        </button-action>
    </permission-component>

</template>
<script>
    import * as actions from '../../constants/actions'
    import buttonAction from './buttonAction.vue'
    import PermissionComponent from '../protection/ProtectedElement.vue'

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
        created(){
            console.log(this.permission)

        },
        props: {
            resourceName: { //Name of the resource
                type: String,
                default: ''
            } ,
            buttonClass: String
        }
    }
</script>