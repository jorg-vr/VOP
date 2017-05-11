<template>
    <div>
        <form-input :placeholder="$t('invoice.payer') | capitalize" :label="$t('invoice.payer') | capitalize"
                    v-model="invoice.companyName"></form-input>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'

    export default {
        props: {
            invoice: Object
        },
        components: {
            TextInputFormGroup,SelectInputFormGroup
        }
    }
</script>


