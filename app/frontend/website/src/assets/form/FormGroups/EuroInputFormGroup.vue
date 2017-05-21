<template>
    <div class="row">
        <div class="form-group">
            <label class="col-xs-3 control-label" :for="name">{{text | capitalize}}</label>
            <p class="col-xs-9 input-icon ">
                <i>€</i>
                <input :type="'number'" class="form-control"
                       :value="object[name]/100"
                       @input="onInput($event.target.value)"
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
            onInput(value){
                this.$set(this.object, this.name, value*100)
            }
        }
    }
</script>
<style>
    .input-icon {
        position: relative;
    }

    .input-icon > i {
        position: absolute;
        display: block;
        transform: translate(0, -50%);
        top: 50%;
        pointer-events: none;
        width: 25px;
        text-align: center;
        font-style: normal;
    }

    .input-icon > input {
        padding-left: 20px;
        padding-right: 0;
    }


</style>