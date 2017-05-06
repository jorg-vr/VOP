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
            <label class="col-xs-3 control-label" :for="name">{{text}}</label>
            <p class="col-xs-9  select-row">
                <select class="form-control select-item" ref="select"
                        :value="object[name]"
                        @change="onInput($event.target.value)"
                        v-validate="rules"
                        :data-vv-name="name"
                        :data-vv-as="text"
                        :has-error="errors.has(name)">
                    <option v-if="hiddenOption" value="" disabled hidden>{{hiddenOption}}</option>
                    <option v-else value="" disabled hidden></option>
                    <option :selected="option[name] === object[name]" v-for="option in options" :value="option[name]">
                        {{option[visibleKey ? visibleKey : name]}}
                    </option>
                </select>
                <button v-if="resetButton" type="button" id="reset" class="btn btn-xs" @click="reset"><i class="fa fa-times"></i></button>
            </p>
            <span v-show="errors.has(name)" class="help is-danger col-xs-offset-3 col-xs-9">{{ errors.first(name) }}</span>
        </div>
    </div>
</template>
<script>
    import formItem from './formItem.vue'
    export default {
        props: {
            rules: String,
            name: String,
            text: String,
            object: Object,
            options: Array,
            hiddenOption: String,
            visibleKey: String, //Key to show of an object
            resetButton: {
                type: Boolean,
                default: true
            }
        },
        components: {
            formItem
        },
        methods: {
            onInput: function (value) {
                this.$set(this.object, this.name, value)
            },
            reset(){
                //TODO: DOM is not being refreshed when this function is called, this is a bug and has effects and validations
                //and selected index
                this.$set(this.object, this.name, '')
                this.$refs.select.selectedIndex=0
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