<!--
A generic button which navigates to the given route once clicked.

@param route: The route to navigate to once clicked.
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
            buttonClass: {
                type: String,
                default: "btn btn-default"
            },
            buttonId: String
        },
    }
</script>
