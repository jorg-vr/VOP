<template>
    <protected-element :permission="permission">
        <button-link :route="route" :buttonClass="'pull-right btn btn-primary btn-add ' + buttonClass">
            {{$t('common.new') | capitalize }} {{$t(resource.name + '.' + resource.name)}}
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
                    actions: actions.CREATE
                },
                route: {
                    name: 'new_' + this.resource.name,
                    params: this.params
                }
            }
        },
        components: {
            buttonLink, ProtectedElement
        },
        props: {
            resource: Object ,
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
