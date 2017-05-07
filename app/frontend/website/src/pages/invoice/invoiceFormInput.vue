<template>
    <div>
        <form-input :placeholder="$t('invoice.payer') | capitalize" :label="$t('invoice.payer') | capitalize"
                    v-model="invoice.companyName"></form-input>
    </div>
</template>
<script>
    import formInput from '../../assets/form/elements/formInput.vue'
    import formSelect from '../../assets/form/elements/formSelect.vue'

    export default {
        props: {
            invoice: Object
        },
        components: {
            formInput,formSelect
        }
    }
</script>


