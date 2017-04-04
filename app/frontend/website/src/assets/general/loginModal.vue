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
						<p id="error" v-show="showError"> sdfsfsd {{$t("login.error") | capitalize }}  </p>
						<div class="input-group" id="username">
							<span class="input-group-addon"><i aria-hidden="true" class="fa fa-user"></i></span>
							<input id="email" type="text" class="form-control" name="email" placeholder="Gebruikersnaam"  v-model="loginInfo
							.username">
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i aria-hidden="true" class="fa fa-lock"></i></span>
							<input id="password" type="password" class="form-control" name="password" placeholder="Wachtwoord"  v-model="loginInfo.password">
						</div>
						<br>
						<!-- TODO SUBMIT on enter -->
						<button type="button" id="login-button" @click="confirmLogin()">{{$t("login.button") | capitalize }} </button>
					</form>
				</div>
			</div>
		</div>
	</div>


</template>

<script>
	import { mapActions,mapGetters } from 'vuex'
	export default {
		data() {
            return {
                loginInfo:{
                	username:'',
                	password:''
                }
            }
        },	
        computed: {
            ...mapGetters([
                'showError'
            ])
        },
        methods: {
            ...mapActions([
                'registerLogin',

            ]),
            confirmLogin:function(){
        		console.log('Logged in as '+this.loginInfo.username)
        		
        		this.registerLogin(this.loginInfo)
                this.$emit('confirmLogin')
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
        margin-top: 12%;
        vertical-align: middle;

    }
    
    .modal-container {
        width: 35%;
        margin: auto;
        background: rgba(0, 0, 0, 0.1);
        padding:25px;
    }

    .modal-header{
        padding: 15px;
        color: white;
        background-color: #304052;
        border:none;
        background: rgba(0, 0, 0, 0.01);
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

    .form-group input{
        border:none;
        width: 100%;
        height: 40px;
        margin-bottom: 10px;
    }

     .form-group input:focus{
        border:none;
    }

</style>