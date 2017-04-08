<template>
    <button-action @click="emitClicked" buttonClass="btn btn-default btn-sm btn-succes" buttonId="success"></button-action>
</template>

<style>

</style>

<script>
    import buttonAction from './buttonAction.vue'

    export default {
        components: {
            buttonAction
        },
        mixins: [buttonAction]
    }
</script>

