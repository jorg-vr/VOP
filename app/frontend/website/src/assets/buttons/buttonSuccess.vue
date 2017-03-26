<template>
    <button-action @click="emitClicked" buttonClass="btn btn-success btn-md" buttonId="success"><i class="fa fa-check"></i></button-action>
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

