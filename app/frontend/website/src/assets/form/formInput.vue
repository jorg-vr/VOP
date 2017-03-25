<template>
    <div class="row">
        <div class="form-group">
            <!--$event.target.value is the value of this input item-->
            <input type="text" class="form-control input-sm" :placeholder="placeholder"
                   v-bind:value="value" v-on:input=updateValue($event.target.value)">
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            placeholder: String,
            value: String
        },
        created(){
            console.log(this.placeholder)
            console.log(this.value)
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
                // Emit the number value through the input event
                this.$emit('input', formattedValue);
            }
        }
    }
</script>