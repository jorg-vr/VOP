<!--
    This page is used to create a new user based on userinput for a first name, last name and email.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("user.user") | capitalize }} {{$t("actions_plural.create") }}</h1>
        </div>
        <user-form :successButtonText="$t('user.user').capitalize() + ' ' +  $t('actions_plural.create')" :failButtonText="$t('common.cancel').capitalize()"  :submit="createUser"></user-form>
    </div>
</template>
<script>
    import UserForm from '../../assets/form/types/userForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            UserForm
        },
        methods: {
            ...mapActions([
                'createUser'
            ])
        }
    }
</script>

