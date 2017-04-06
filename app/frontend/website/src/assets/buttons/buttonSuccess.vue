<template>
    <button-action @click="emitClicked" buttonClass="btn btn-success btn-md" buttonId="success"></button-action>
</template>
<script>
    import buttonAction from './buttonAction.vue'

    export default {
        components: {
            buttonAction
        },
        mixins: [buttonAction]
    }
</script>

