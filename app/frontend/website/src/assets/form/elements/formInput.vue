<!--
Component usable for requesting user input with a text field.

@param placeholder (optional): placeholder for the text input field
@param label (optional): label which preceder the input field
@param value (optional): initial field for the input field

-->


<template>
    <div class="row">
        <div class="form-group">
            <label class="col-xs-3 control-label" :for="name">{{text | capitalize}}</label>
            <p class="col-xs-9">
                <input type="text" class="form-control" ref="input"
                       :value="object[name]"
                       @input="onInput($event.target.value)"
                       v-validate="rules"
                       :data-vv-name="name"
                       :data-vv-as="text"
                       :has-error="errors.has(name)"
                       :placeholder="text.capitalize()">
            </p>
            <span v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">{{ errors.has(name) ? errors.first(name) : '' | capitalize   }}</span>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            rules: String,
            name: String,
            text: String,
            object: Object,
        },
        methods: {
            onInput(value){
                this.$set(this.object, this.name, value)
            }
        }
    }
</script>
<style>
    .form-control, input {
        border-width: 1.5px;
    }
    .form-group .col-xs-9 {
        margin-bottom: 0;
    }
    [has-error='true'],
    [has-error='true']:focus{
        border-color: #ff3860;
    }
    .help.is-danger {
        display: block;
        font-size: 14px;
        color: #ff3860;
    }
</style>
