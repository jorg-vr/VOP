<!--
A button usable for navigating to a certain page if the action on the current page fails or if the user decides to cancel the current action.

@param route: The route to return to when clicking this button
-->

<template>
    <button-link :route="route" buttonClass="btn btn-danger btn-md" buttonId="fail">
        <i  class="fa fa-times"></i>
    </button-link>
</template>
<script>
    import buttonLink from './buttonLink.vue'

    export default {
        components: {
            buttonLink
        },
        props: {
            route: Object,
        }
    }
</script>
