<template>
    <div>
        <form-input :placeholder="$t('invoice.payer') | capitalize" :label="$t('invoice.payer') | capitalize"
                    v-model="invoice.companyName"></form-input>
    </div>
</template>
<script>
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'

    export default {
        props: {
            invoice: Object
        },
        components: {
            formInput,formSelect
        }
    }
</script>


