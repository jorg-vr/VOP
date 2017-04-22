<!--
A button used for navigating to the 'edit' page of the specified resource.
The button will only be shown if the user has the correct permissions.

@param resource: The resource to which has to be navigated
@param params (optional): Parameters for the route
@param buttonClass (optional): The HTML class for this button
-->

<template>
    <protected-element :permission="permission">
        <button-link :route="route"  buttonClass="btn btn-xs btn-info">
            <i aria-hidden="true" class="fa fa-pencil"></i>
        </button-link>
    </protected-element>
</template>
<style>
.btn-info {
    margin-top: 17px;
}
</style>
<script>
    import actions from '../../constants/actions'
    import buttonLink from './buttonLink.vue'
    import ProtectedElement from '../protection/ProtectedElement.js'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resource,
                    actions: actions.UPDATE
                },
                route: {
                    name: 'edit_' + this.resource.name,
                    params: this.params
                }
            }
        },
        components: {
            buttonLink, ProtectedElement
        },
        props: {
            resource: Object,
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
