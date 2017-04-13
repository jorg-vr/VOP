<template>
    <button type="button" :class="buttonClass" @click="emitClicked" :id="buttonId">
        <slot></slot>
    </button>
</template>
<script>
    import PermissionComponent from '../general/PermissionComponent.vue'
    export default {
        props: {
            action: Function,
            buttonClass: String,
            buttonId: String,
        },
        methods: {
            emitClicked(){
                this.$emit('click')
            }
        }
    }
</script>
