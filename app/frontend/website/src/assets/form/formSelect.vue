<template>
    <form-item>
        <select class="form-control" :value="value" @change="updateValue($event.target.value)">
            <option value="" disabled hidden>Selecteer {{label}}</option>
            <option :selected="option.id === value" v-for="option in options" :value="option.id">
                {{option[optionKey]}}
                    </option>
        </select>
    </form-item>

</template>
<script>
    import formItem from './formItem.vue'
    export default {
        props: {
            label: String,
            value: String,
            options: Array,
            optionKey: String //Key to show of an object
        },
        components: {
            'form-item': formItem
        },
        methods: {
            //TODO: With props we can add validation functions for each input item.
            updateValue: function (value) {
                var formattedValue = value.trim();
                // If the value was not already normalized,
                // manually override it to conform
                if (formattedValue !== value) {
                    this.$refs.input.value = formattedValue;

                }
                // Emit the formatted value through the input event
                this.$emit('input', formattedValue);
            }
        }
    }
</script>