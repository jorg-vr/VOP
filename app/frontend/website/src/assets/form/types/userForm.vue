<!--
    This is a form to create/update a user.
-->
<template>
    <div>
        <form-component v-if="user">
            <form-input :placeholder="$t('user.firstName') | capitalize" :label="$t('user.firstName') | capitalize"
                        v-model="user.firstName"></form-input>

            <form-input :placeholder="$t('user.lastName') | capitalize" :label="$t('user.lastName') | capitalize"
                        v-model="user.lastName"></form-input>

            <form-input :placeholder="$t('user.email') | capitalize" :label="$t('user.email') | capitalize"
                        v-model="user.email"></form-input>
        </form-component>
        <div class="row">
            <button-success @click="proceed"></button-success>
            <button-fail :route="{name: 'users'}"></button-fail>
        </div>
    </div>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import formInput from '../elements/formInput.vue'
    import buttonFail from '../../buttons/buttonFail.vue'
    import buttonSuccess from '../../buttons/buttonSuccess.vue'

    export default {
        components: {
            formComponent, formInput, buttonFail, buttonSuccess
        },
        props: {
            submit: Function, //Function to create the fleet.
            oldUser: Object
        },
        computed: {
            user(){
                if(this.oldUser === undefined) {
                    return {address: {}}

                }
                else {
                    return this.oldUser
                }
            }
        },
        methods: {
            proceed(){
                this.submit({user: this.user}).then(() => {
                    this.$router.push({name: 'users'})
                })
            }
        }
    }
</script>
