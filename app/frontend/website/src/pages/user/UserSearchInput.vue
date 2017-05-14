<!--
All of the fields for user input for the user form

@param user: This object will be configured with the input of this component.
-->
<template>
    <div>
        <text-input-form-group :object="user" name="firstName" :text="$t('user.firstName')"></text-input-form-group>
        <text-input-form-group :object="user" name="lastName" :text="$t('user.lastName')"></text-input-form-group>
        <text-input-form-group :object="user" name="email" :text="$t('user.email')"></text-input-form-group>
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
        },
    }
</script>