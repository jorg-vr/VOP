<!--
A button usable for navigating to the previous page. This previous page has to be decided by the programmer.

@param route: The route to return to when clicking this button
-->

<template>
    <button-link :route="route"  buttonClass="btn btn-default pull-left" buttonId="back">{{$t('common.back') | capitalize }}</button-link>
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