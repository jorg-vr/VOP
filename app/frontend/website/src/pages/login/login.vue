<!--
TODO: document this page.
-->
<template>
    <div class="modal-mask" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    <h3> {{$t("login.header") | capitalize }} Solvas Fleet</h3>
                    <h5> {{$t("login.text") | capitalize }}  </h5>
                </div>
                <div class="modal-body">
                    <form id="loginform">
                        <p id="error" v-show="showError"> {{$t("login.error") | capitalize }}  </p>
                        <div class="input-group" id="username">
                            <span class="input-group-addon"><i aria-hidden="true" class="fa fa-user"></i></span>
                            <input id="email" type="text" class="form-control" name="email"  v-bind:placeholder="$t('login.username')" v-model="credentials
							.login">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon"><i aria-hidden="true" class="fa fa-lock"></i></span>
                            <input id="password" type="password" class="form-control" name="password" v-bind:placeholder="$t('login.password')"  v-model="credentials.password">
                        </div>
                        <br>
                        <button-action id="login-button" @click="confirmLogin()"> {{$t("login.button") | capitalize }} </button-action>
                    </form>
                </div>
            </div>
        </div>
    </div>


</template>

<script>
    import { mapActions, mapMutations, mapGetters } from 'vuex'
    import buttonAction from '../../assets/buttons/buttonAction.vue'
    export default {
        data() {
            return {
                credentials:{
                    login:'admin@solvas.be',
                    password:123
                },
                showError: false
            }
        },
        components: {
            buttonAction
        },
        computed: {
            ...mapGetters([
                'hasActiveAccount', 'nextRoute'
            ])
        },
        methods: {
            ...mapActions([
                'authenticate','fetchAccount'

            ]),
            ...mapMutations({
                setActiveAccount: 'SET_ACTIVE_ACCOUNT'
            }),
            confirmLogin:function(){
                // Get webtoken and account information
                this.authenticate(this.credentials).then(() => {
                    // check if login was succesfull
                    if(!(this.hasActiveAccount)){
                        // Failed
                        this.showError=true
                    }
                    else{
                        // Succes, return to home
                        if(this.nextRoute.path !== null){
                            this.$router.push({name: this.nextRoute.name, params: this.nextRoute.params})
                        }
                        else {
                            this.$router.push({name: 'home'})
                        }
                    }
                })


            }
        }
    }
</script>

<style>

    #login-button {
        width: 100%;
        height: 40px;
        background: #1AB394;
        border: 0;
        font-size: 14px;
        font-weight: 300;
        color: #fff;
    }

    #login-button:hover{
        background:#009D7E;
        color:white;
    }
    #username{
        margin-bottom: 20px;
    }

    #closebtn{
        margin-top: 40px;
    }

    #error{
        color:#cc0000;
    }

    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: #304052;
        transition: opacity .3s ease;
        text-align: center;
    }
    .modal-wrapper {
        margin-top: 8%;
        vertical-align: middle;

    }

    .modal-container {
        width: 40%;
        margin: auto;
        background-color: #304052;
        padding:25px;
    }

    .modal-header{
        padding: 15px;
        color: white;
        background-color: #304052;
        border:none;
        /*background: rgba(0, 0, 0, 0.01); OLD STYLE */
    }


    .modal-body {
        color: #304052;
        font-size: 14px;
        font-weight: 600;
    }
    .modal-footer{
        border:none
    }


    .modal-footer button{
        background:#1AB394;
        color:white;
        width: 100px;
        margin: 0px 10px 0px 10px;
        font-weight: 600;
    }

    .modal-footer button:hover{
        background:#009D7E;
        color:white;
    }

</style>
