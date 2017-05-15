<!--
Generic component for a form. Every form should be encapsulated in this component.

@param actions: The action this form is intended for.
@param resource: The name of the resource configured by this form
@param object: The resource configured by this form
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ submitText }}</h1>
        </div>
        <part class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7"
              :back="back" :actions="actions" :resource="resource" :object="object" :ids="ids" :customSubmit="customSubmit">
            <slot></slot>
        </part>
    </div>
</template>
<script>
    import {getResourceActionText} from '../../utils/utils'
    import part from './AbstractFormPart.vue'

    export default {
        data(){
            return {
                submitText:  getResourceActionText(this.resource.name, this.actions.name)
            }
        },
        components: {
            part
        },
        props: {
            back: Object, //link to previous page
            actions: Object, //The action of this form
            resource: Object, //The name of the resource configured by this form
            object: [Object,Array], //The resource configured by this form
            ids: Object, //Object with id's for creating the correct POST/PUT route.
            customSubmit: Function //Custom submit function (optional)
        }
    }
</script>
