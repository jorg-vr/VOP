<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("client.client") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <client-form :oldClient=client :submit="updateClient"></client-form>
    </div>
</template>
<script>
    import ClientForm from '../../assets/form/types/clientForm.vue'
    import {mapGetters, mapActions} from 'vuex'


    export default {
        components: {
            ClientForm
        },
        created(){
            this.fetchClient({id: this.$route.params.id})
        },
        computed: {
            ...mapGetters([
                'client'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'updateClient'
            ])
        }
    }
</script>

