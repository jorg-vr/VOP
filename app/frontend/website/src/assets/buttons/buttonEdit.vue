<template>
    <permission-component :permission="permission">
        <button-link :route="route"  buttonClass="btn btn-md btn-info" buttonId="edit">
            <i aria-hidden="true" class="fa fa-pencil"></i>
        </button-link>
    </permission-component>

</template>
<script>
    import * as actions from '../../constants/actions'
    import buttonLink from './buttonLink.vue'
    import PermissionComponent from '../general/PermissionComponent.vue'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resourceName.toUpperCase(),
                    actions: actions.UPDATE
                },
                route: {
                    name: 'edit_' + this.resourceName,
                    params: this.params
                }
            }
        },
        components: {
            buttonLink, PermissionComponent
        },
        props: {
            resourceName: String, //Name of the resource
            params: { //Initial params for adding
                type: Object,
                default(){
                    return {}
                }
            },
            buttonClass: String
        }
    }
</script>