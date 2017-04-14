<template>
    <protected-element :permission="permission">
        <button-link :route="route"  buttonClass="btn btn-md btn-info" buttonId="edit">
            <i aria-hidden="true" class="fa fa-pencil"></i>
        </button-link>
    </protected-element>

</template>
<script>
    import * as actions from '../../constants/actions'
    import buttonLink from './buttonLink.vue'
    import ProtectedElement from '../protection/ProtectedElement.js'

    export default {
        data() {
            return {
                permission: {
                    resource: this.resource.value,
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