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
    import PermissionComponent from '../protection/ProtectedElement.vue'

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
        created(){
            console.log(this.permission)
        },
        components: {
            buttonLink, PermissionComponent
        },
        props: {
            resourceName: { //Name of the resource
                type: String,
                default: ''
            } ,
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