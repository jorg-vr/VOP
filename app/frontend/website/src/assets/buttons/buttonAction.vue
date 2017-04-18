<!--
A generic button which emits a click event when clicked.
This button can be used to call a function when the button is clicked.

@param buttonClass (optional): The HTML class for this button
@param buttonId (optional): The HTML id for this button
-->

<template>
    <button type="button" @click="$emit('click')" :class="buttonClass" :id="buttonId">
        <slot></slot>
    </button>
</template>
<script>
    export default {
        props: {
            buttonClass: String,
            buttonId: String,
        }
    }
</script>
