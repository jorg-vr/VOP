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