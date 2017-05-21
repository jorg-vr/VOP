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
                <input type="date" class="form-control"
                       :value="date"
                       @input="onInput($event.target.value)"
                       v-validate="validator"
                       :data-vv-name="name"
                       :data-vv-as="text"
                       :has-error="errors.has(name)"
                       :placeholder="text.capitalize()">
            </p>
            <span v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">
                {{ errors.has(name) ? errors.first(name) : '' | capitalize }}
            </span>
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
        computed: {
            validator(){
                return this.rules ? this.rules : ''
            },
            date(){
                if(this.object[this.name]){
                    //Only show the date part of the date time.
                    return this.object[this.name].slice(0,10);
                }

            }
        },
        methods: {
            onInput(value){
                this.$set(this.object, this.name, new Date(value).toISOString())
            }
        }
    }
</script>
