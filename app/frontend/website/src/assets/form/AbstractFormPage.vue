<!--
This page is used to edit or create a certain client.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <form-component v-if="object" :actions="actions" :resource="resource" :object="object">
            <slot></slot>
        </form-component>
    </div>
</template>
<script>
    import formComponent from './AbstractForm.vue'
    import actions from '../../constants/actions'
    import {getResourceActionText} from '../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('client', this.actions.name),
            }
        },
        components: {
            formComponent
        },
        props: {
            object: Object,
            resource: Object,
            id: String,
            actions: Object //The action for this form.
        }
    }
</script>