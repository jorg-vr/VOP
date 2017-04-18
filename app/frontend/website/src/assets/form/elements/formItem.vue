<!--
A generic component for user input in a form.

@param label (optional): The label which should precede the HTML select item.
-->

<template>
    <div class="row">
        <div class="form-group">
            <label v-if="label" class="col-xs-3 control-label">{{label}}</label>
            <div :class="getColumnClass">
                <slot></slot>
            </div>

        </div>
    </div>
</template>
<style>
.form-horizontal .control-label{
    text-align: left;
}
</style>
<script>
    export default {
        props: {
            label: String
        },
        computed: {
            getColumnClass(){
                if(this.label){
                    return "col-xs-9"
                }
                else {
                    return ""
                }
            }
        }
    }
</script>
