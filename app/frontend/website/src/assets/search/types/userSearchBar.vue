<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(user)" @reset="$emit('search', '')"  :title="$t('user.users')">
        <user-form-input :user="user"></user-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import userFormInput from '../../form/types/userFormInput.vue'
    export default {
        data() {
            return {
                user: {}
            }
        },
        components: {
            searchBar, userFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
