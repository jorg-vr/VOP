<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(client)" @reset="$emit('search', '')" :title="$t('client.clients')">
        <client-form-input :client="client"></client-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import clientFormInput from '../../form/types/clientFormInput.vue'
    export default {
        data() {
            return {
                client: {address: {}}
            }
        },
        components: {
            searchBar, clientFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
