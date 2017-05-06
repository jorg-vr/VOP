<template>
    <div>
        <form-input :placeholder="$t('invoice.payer') | capitalize" :label="$t('invoice.payer') | capitalize"
                    v-model="invoice.companyName"></form-input>
    </div>
</template>
<script>
    import formInput from '../FormGroups/TextInputFormGroup.vue'
    import formSelect from '../FormGroups/SelectInputFormGroup.vue'

    export default {
        props: {
            invoice: Object
        },
        components: {
            formInput,formSelect
        }
    }
</script>


