<!--
All of the fields for user input for the user form

@param user: This object will be configured with the input of this component.
-->
<template>
    <div>
        <text-input-form-group :object="user" name="firstName" :text="$t('user.firstName')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="user" name="firstName" :text="$t('user.lastName')" :rules="'required'"></text-input-form-group>
        <text-input-form-group :object="user" name="email" :text="$t('user.email')" :rules="'required|email'"></text-input-form-group>
        <text-input-form-group type="password" :object="user" name="password" :text="$t('user.password')" :rules="'required'"></text-input-form-group>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'

    export default {
        props: {
            user: Object
        },
        components: {
            TextInputFormGroup
        }
    }
</script>