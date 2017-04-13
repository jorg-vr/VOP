<template>
    <permission-component :permission="permission">
        <button-link :route="route" :buttonClass="'pull-right btn btn-primary btn-add ' + buttonClass">
            {{$t('common.new') | capitalize }} {{$t(resourceName + '.' + resourceName)}}
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
                    actions: actions.CREATE
                },
                route: {
                    name: 'new_' + this.resourceName,
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
