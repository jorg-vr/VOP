np<!--
This component can be used to let the user select between a list of specified values.

@param options: All of the option which can be selected
@param optionKey: The property of the object to be displayed to the user
@param optionProperty: the property of the object which is emitted to the parent component on change. Default: id
@param label (optional): The label preceding the HTML select element
@param value (optional): The initially selected value of the select element
@param hiddenOption (optional): The displayed value if there's nothing selected.
@param inputId (optional): The id of the HTML select element.
@param resetButton (optional): If this boolean is true a reset button will be display. Default: False
@param selectClass (optional): The class for the HTML select element.
-->
<template>
    <div class="row">
        <div class="form-group">
            <label class="col-xs-3 control-label" :for="name">{{text | capitalize}}</label>
            <p class="col-xs-9  select-row">
                <select class="form-control select-item" ref="select"
                        :value="object[name]"
                        @change="onInput($event.target.value)"
                        v-validate="rules"
                        :data-vv-name="name"
                        :data-vv-as="text"
                        :has-error="errors.has(name)">
                    <option value="" disabled hidden>{{$t('actions.select', {subject: text}).capitalize()}}</option>
                    <option  v-for="optionObject in options"
                             :selected="optionObject[property] === object[name]"
                             :value="optionObject[property]">
                        {{optionText(optionObject)}}

                    </option>
                </select>
                <button v-if="resetButton" type="button" id="reset" class="btn btn-xs" @click="reset"><i class="fa fa-times"></i></button>
            </p>
            <span v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">
                {{ errors.has(name) ? errors.first(name) : '' | capitalize  }}
            </span>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            rules: String,
            name: String,
            optionPropertyName: String, //Name of the property in the object.
            text: String,
            object: Object,
            options: Array,
            hiddenOption: String,
            visibleKey: [Function, String], //Key to show of an object
            resetButton: {
                type: Boolean,
                default: true
            }
        },
        computed: {
            property() {
                return this.optionPropertyName ? this.optionPropertyName : this.name
            }
        },
        methods: {
            onInput: function (value) {
                this.$set(this.object, this.name, value)
            },
            reset(){
                this.$set(this.object, this.name, '')
                this.$refs.select.selectedIndex=0
                this.$validator.validate(this.name, '')
            },
            optionText(option){
                if (option[this.visibleKey] instanceof Function) {
                    return option[this.visibleKey]().capitalize();
                }
                else {
                    return option[this.visibleKey ? this.visibleKey : this.name].capitalize()
                }
            }
        }
    }
</script>
<style>
    .select-row {
        display: flex
    }
    #reset{
        width: 50px;
    }
</style>