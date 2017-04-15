<template>
    <form-item :label="label">
        <div class="select-row">
            <select :class="'form-control select-item '+selectClass" :value="value" @change="updateValue($event.target.value)" :id="inputId" ref="select">
                <option v-if="hiddenOption" value="" disabled hidden>{{hiddenOption}}</option>
                <option v-else value="" disabled hidden></option>
                <option :selected="option[property] === value" v-for="option in options" :value="option[property]">
                    {{option[optionKey]}}
            </option>
            </select>
            <button v-if="resetButton" type="button" id="reset" class="btn btn-xs" @click="reset"><i class="fa fa-times"></i></button>
        </div>
    </form-item>

</template>
<script>
    import formItem from './formItem.vue'
    export default {
        props: {
            label: String,
            value: String, //Initial value
            options: Array,
            hiddenOption: String,
            inputId: String,
            optionKey: String, //Key to show of an object
            optionProperty: String, //Value of this select item
            resetButton: {
                type: Boolean,
                default: false
            },
            selectClass: {
                type: String,
                default: ''
            }
        },
        components: {
            formItem
        },
        computed: {
            property() {
                if(!this.optionProperty){
                    return 'id'
                }
                else {
                    return this.optionProperty
                }
            }
        },
        methods: {
            updateValue: function (value) {
                this.$emit('input', value);
            },
            reset(){
                this.updateValue('')
                this.$refs.select.value = ''
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