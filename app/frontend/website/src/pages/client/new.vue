<!--
    This page is used to create a new client based on userinput for a name, an address, a VAT number and a phone number.
-->
<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1>{{ $t("client.client") | capitalize }} {{$t("actions_plural.create") }}</h1>
     </div>  
     <client-form :submit="createClient"></client-form>
 </div>
</template>
<script>
    import ClientForm from '../../assets/form/types/clientForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            ClientForm
        },
        methods: {
            ...mapActions([
                'createClient'
            ])
        }
    }
</script>

