<!--
Component usable for requesting user input with a text field.

@param placeholder (optional): placeholder for the text input field
@param label (optional): label which preceder the input field
@param value (optional): initial field for the input field

-->

<template>
    <form-item :label="label">
        <!--$event.target.value is the value of this input item-->
        <input type="text" class="form-control" :placeholder="placeholder"
               :value="value" @input="$emit('input', $event.target.value)">
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
        }
    }
</script>