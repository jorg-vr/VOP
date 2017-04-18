<!--
TODO: document this file
-->

<template>
	<div class="modal-mask"  transition="modal">
		<div class="modal-wrapper">
			<div class="modal-container">
				<div class="modal-header" id="modalHeader">
					<button type="button" id="closebtn" class="close" @click="$emit('cancelModal')" ><i class="fa fa-times" aria-hidden="true"></i></button>
					<h3> {{ $t("modal.title") | capitalize }} </h3>
				</div>
				<div class="modal-body">
					<p> {{ $t("modal.text") | capitalize }} </p>
					<div class="modal-footer">
						<button type="button" class="btn" @click="$emit('confirmModal')">  {{ $t("modal.button1") | capitalize }}  </button>
						<button type="button" class="btn" @click="$emit('cancelModal')"> {{ $t("modal.button2") | capitalize }}  </button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<style scoped>


	.modal-body p{
		margin-top: 15px;
	}
	.modal-header h3{
		margin-top: 15px;
	}
	#closebtn{
		margin-top: 15px;
	}
	.modal-container {
		width: 35%;
		margin: auto;
		background: #304052;
	}

	.modal-mask {
		position: fixed;
		z-index: 9998;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, .8);
		transition: opacity .3s ease;
		text-align: center;
	}
	.modal-wrapper {
        margin-top: 12%;
        vertical-align: middle;

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
        background-color: white;
    }
    .modal-footer{
        border:none
    }

    .modal-footer button{
        background:#1AB394;
        color:white;
        width: 100px;
        margin-right:70px;
        font-weight: 600;
    }

    .modal-footer button:hover{
            background:#009D7E;
            color:white;
    }

 
</style>