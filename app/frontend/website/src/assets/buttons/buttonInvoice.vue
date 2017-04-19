<template>
    <router-link :to="{name: 'invoices', params: {companyId: companyId}, companyId: companyId}">
        <button type="button" :class="'pull-right btn btn-primary btn-add ' + buttonClass" :id="buttonId">
            {{$t("invoice.invoices") | capitalize }}
        </button>
    </router-link>
</template>
<script>
    export default {
        props: {
            companyId: String,
            buttonClass: String,
            buttonId: String
        },
    }
</script>
