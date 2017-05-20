<!--
TODO: document this page.
-->
<template>
    <div class="modal-mask" id="loginMask">
        <div class="modal-wrapper" id="loginWrapper">
            <div class="modal-container" id="loginContainer">
                <div class="modal-header" id="loginHeader">
                    <h3> {{$t("login.header") | capitalize }} Solvas Fleet</h3>
                    <h5> {{$t("login.text") | capitalize }}  </h5>
                </div>
                <div class="modal-body col-lg-offset-4 col-lg-4 col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8" id="loginBody">
                    <form id="loginform">
                        <p id="error" v-show="showError"> {{$t("login.error") | capitalize }}  </p>
                        <div class="input-group" id="username">
                            <span class="input-group-addon"><i aria-hidden="true" class="fa fa-user"></i></span>
                            <input id="email" type="text" class="form-control" name="email"  v-bind:placeholder="$t('login.username')" v-model="credentials.login" @keyup.enter="confirmLogin()">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon"><i aria-hidden="true" class="fa fa-lock"></i></span>
                            <input id="password" type="password" class="form-control" name="password" v-bind:placeholder="$t('login.password')"  v-model="credentials.password" @keyup.enter="confirmLogin()">
                        </div>
                        <br>
                        <button type="button" id="login-button" :class='buttonClass' @click="confirmLogin()">
                            <i v-if="loading" class="fa fa-spinner fa-spin"></i>
                            <b v-else>{{$t("login.button") | capitalize }}</b>
                        </button>
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
                    login:'patrick.oostvogels@solvas.be',
                    password:'1h8xE660mn'
                },
                showError: false,
                loading: false
            }
        },
        components: {
            buttonAction
        },
        computed: {
            ...mapGetters([
                'nextRoute',
            ]),
            buttonClass() {
                return this.loading ? 'loading' : ''
            }
        },
        methods: {
            ...mapActions([
                'authenticate'
            ]),
            ...mapMutations([
                'setLoading'
            ]),
            confirmLogin:function(){
                this.showError=false;
                if(!this.loading){
                    this.loading = true
                    // Get webtoken and account information
                    this.authenticate(this.credentials).then(() => {
                        // Succes, return to home
                        if(this.nextRoute.path !== null){
                            this.$router.push({name: this.nextRoute.name, params: this.nextRoute.params})
                        }
                        else {
                            this.$router.push({name: 'homeClient'})
                        }
                        this.loading = false
                    }, () => {
                        this.showError=true
                        this.loading = false
                    })
                }
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

    #loginMask {
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
    #loginWrapper{
        margin-top: 8%;
        vertical-align: middle;

    }

    #loginContainer{
        margin: auto;
        background-color: #304052;
        padding:25px;
    }

    #loginHeader{
        padding: 15px;
        color: white;
        background-color: #304052;
        border:none;
       
    }


    #loginBody{
        color: #304052;
        font-size: 14px;
        font-weight: 600;
    }

    .loading:hover,
    .loading {
        opacity: 0.6;
        cursor: auto;
        background: #1AB394;
    }


</style>
