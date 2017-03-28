<template>
    <div class="row">
        <div class="input-group search-form">
            <label>Zoeken</label>
            <form-input @input="onInput"></form-input>
            <span class="input-group-btn">
            </span>
        </div>
    </div>
</template>

<script>
    import formInput from '../form/elements/formInput.vue'
    export default {
        components: {
            formInput
        },
        methods: {
            onInput(value){
                this.$emit('search', value)
            }
        }
    }
</script>
<style>
    .search-form {
        margin-bottom: 20px;
        width: 500px;
    }
</style>