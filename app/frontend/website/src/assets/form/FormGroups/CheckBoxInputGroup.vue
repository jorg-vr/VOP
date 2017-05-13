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
            <label class="switch">
                <input type="checkbox"
                       class=""
                       v-model="object[name]"
                       v-validate="validator"
                       :data-vv-name="name"
                       :data-vv-as="text"
                       :has-error="errors.has(name)"
                       :placeholder="text.capitalize()">
                <div class="slider round"></div>
            </label>
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
        created(){

        },
        computed:{
            validator(){
                return this.rules ? this.rules : ''
            }
        }
    }
</script>
<style>
    /* The switch - the box around the slider */
    .switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 20px;
        margin-top: 10px;
        margin-left: 20px;
    }

    /* Hide default HTML checkbox */
    .switch input {display:none;}

    /* The slider */
    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 14px;
        width: 14px;
        left: 3px;
        bottom: 3px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
    }

    input:checked + .slider {
        background-color: #2196F3;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px #2196F3;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(40px);
        -ms-transform: translateX(40px);
        transform: translateX(40px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 34px;
    }

    .slider.round:before {
        border-radius: 50%;
    }
</style>

