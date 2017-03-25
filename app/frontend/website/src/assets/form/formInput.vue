<template>
    <form-item :label="label">
        <!--$event.target.value is the value of this input item-->
        <input type="text" class="form-control input-sm" :placeholder="placeholder"
               :value="value" @input="updateValue($event.target.value)">
    </form-item>
</template>
<script>
    import formItem from './formItem.vue'
    export default {
        props: {
            placeholder: String,
            label: String,
            value: [Number, String]
        },
        components: {
            formItem
        },
        methods: {
            //TODO: With props we can add validation functions for each input item.
            updateValue: function (value) {
                let formattedValue = value.trim();
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