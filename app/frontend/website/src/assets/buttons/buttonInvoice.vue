<template>
    <router-link :to="{name: 'invoices', params: {companyId: id}}">
        <button type="button" :class="'pull-right btn btn-primary btn-add ' + buttonClass" :id="buttonId">
            {{$t("invoice.invoices") | capitalize }}
        </button>
    </router-link>
</template>
<script>
    export default {
        props: {
            id: String,
            buttonClass: String,
            buttonId: String
        },
    }
</script>
