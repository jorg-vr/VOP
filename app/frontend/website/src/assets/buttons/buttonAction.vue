<template>
    <button type="button" @click="$emit('click')" :class="buttonClass" :id="buttonId">
        <slot></slot>
    </button>
</template>
<script>
    import ProtectedElement from '../protection/ProtectedElement.vue'
    export default {
        props: {
            action: Function,
            buttonClass: String,
            buttonId: String,
        }
    }
</script>
