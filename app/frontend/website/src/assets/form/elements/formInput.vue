<template>
    <form-item :label="label">
        <!--$event.target.value is the value of this input item-->
        <input type="text" class="form-control" :placeholder="placeholder"
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
            updateValue (value) {
                // Emit the formatted value through the input event
                this.$emit('input', value)
            }
        }
    }
</script>