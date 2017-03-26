<template>
    <button-action @click="emitClicked" buttonClass="btn btn-md btn-danger">
        <i aria-hidden="true" class="fa fa-trash"></i>
    </button-action>
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