<!--
Component usable for requesting user input with a checkbox field.

@param placeholder (optional): placeholder for the text input field
@param label (optional): label which preceder the input field
@param value (optional): initial field for the input field

-->


<template>
    <div class="row">
        <div class="form-group">
            <label class="col-xs-3 control-label" :for="name">{{text | capitalize}}</label>
            <p class="col-xs-9">
                <input type="checkbox"
                       class="form-control"
                       :value="object[name]"
                       @change="onChange($event.target.checked)"
                       v-validate="validator"
                       :data-vv-name="name"
                       :data-vv-as="text"
                       :has-error="errors.has(name)"
                       :placeholder="text.capitalize()">
            </p>
            <span v-if="validator!==''" v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">
                {{ errors.has(name) ? errors.first(name) : '' | capitalize   }}
            </span>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            type: String, //Type of input to be accepted
            rules: String,
            name: String,
            text: String,
            object: Object,
        },
        computed:{
            validator(){
                return this.rules ? this.rules : ''
            }
        },
        methods: {
            onChange(value){
                console.log(value);
                this.$set(this.object, this.name, value)
            }
        }
    }
</script>

