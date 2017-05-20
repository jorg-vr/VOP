<!--
A button used for navigating to the 'new' page of the specified resource.
The button will only be shown if the user has the correct permissions.

@param resource: The resource to which has to be navigated
@param params (optional): Parameters for the route
@param buttonClass (optional): The HTML class for this button
-->

<template>
    <protected-element :permission="permission">
        <button-link :route="route" :buttonClass="'pull-right btn btn-primary btn-add ' + buttonClass">
            {{ submitText }}
        </button-link>
    </protected-element>
</template>
<script>
    import actions from '../../constants/actions'
    import buttonLink from './buttonLink.vue'
    import ProtectedElement from '../protection/ProtectedElement.js'
    import {getResourceActionText} from '../../utils/utils'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resource,
                    actions: actions.CREATE
                },
                route: {
                    name: 'new_' + this.resource.name,
                    params: this.params
                },
                submitText: getResourceActionText(this.resource.name, actions.CREATE.name)
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
