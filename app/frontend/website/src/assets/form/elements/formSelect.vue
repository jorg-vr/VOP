<template>
    <form-item :label="label">
        <select class="form-control select-item" :value="value" @change="updateValue($event.target.value)" :id="inputId" ref="select">
            <option v-if="hiddenOption" value="" disabled hidden>{{hiddenOption}}</option>
            <option v-else value="" disabled hidden></option>
            <option :selected="option.id === value" v-for="option in options" :value="option.id">
                {{option[optionKey]}}
            </option>
        </select>
        <button type="button" id="advanced-search" class="btn btn-xs" @click="reset"><i class="fa fa-times"></i></button>
    </form-item>

</template>
<script>
    import formItem from './formItem.vue'
    export default {
        props: {
            label: String,
            value: String,
            options: Array,
            hiddenOption: String,
            inputId: String,
            optionKey: String //Key to show of an object
        },
        components: {
            formItem
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
    .select-item {
        width: 95%;
        float: left;
    }
    #advanced-search {
        width: 5%;
        float: left;
        height: 34px;
        margin: 0px;
    }
</style>