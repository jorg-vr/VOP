<!--
A generic button which emits a click event when clicked.
This button can be used to call a function when the button is clicked.

@param buttonClass (optional): The HTML class for this button
@param buttonId (optional): The HTML id for this button
-->

<template>
    <router-link :to="route">
        <button type="button" :class="buttonClass" :id="buttonId">
            <slot></slot>
        </button>
    </router-link>
</template>
<script>
    export default {
        props: {
            route: Object,
            buttonClass: String,
            buttonId: String
        },
    }
</script>
