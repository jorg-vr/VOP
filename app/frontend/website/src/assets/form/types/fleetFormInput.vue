<template>
    <div>
        <form-input :placeholder="$t('common.name') + ' ' + $t('fleet.fleet') | capitalize" v-model="fleet.name"></form-input>
        <form-select optionKey="name" :options="clients" v-model="fleet.company"
                     :hiddenOption="$t('actions.select') + ' ' + $t('client.company')| capitalize ">
        </form-select>
    </div>

</template>
<script>
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'

    export default {
        props: {
            clients: Array,
            fleet: Object
        },
        components: {
            formInput, formSelect
        }
    }
</script>