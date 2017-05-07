<!--
All of the fields for user input for the user form

@param user: This object will be configured with the input of this component.
-->
<template>
    <div>
        <form-input :placeholder="$t('user.firstName') | capitalize" :label="$t('user.firstName') | capitalize"
                    v-model="user.firstName"></form-input>

        <form-input :placeholder="$t('user.lastName') | capitalize" :label="$t('user.lastName') | capitalize"
                    v-model="user.lastName"></form-input>

        <form-input :placeholder="$t('user.email') | capitalize" :label="$t('user.email') | capitalize"
                    v-model="user.email"></form-input>
    </div>
</template>
<script>
    import formInput from '../../assets/form/elements/formInput.vue'

    export default {
        props: {
            user: Object
        },
        components: {
            formInput
        }
    }
</script>