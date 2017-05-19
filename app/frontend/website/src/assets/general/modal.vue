<!--
 Generic component for displaying modals with a confirm and/or cancel option. 
 This component will emit events to its parent who then can handles these events.

 Emitted events are:
  confirm option: 'confirmModal' on click-event
  cancel option: 'cancelModal' on click event 

@param confirmButtonText: The text to be shown on the confirm option button. Required to have a confirm option.
@param cancelButtonText: The text to be shown on the cancel option button. Required to have a cancel option.
@param optionalButtonText: The text to be shown on the option button. Required to have an optional button.
@param modalHeaderTitle: The title to be shown in the modal header. Description of what the modal is intended for.
@param modalBodyText: The text to be shown in the modal body. General modal description.
@param endDate: The text to be shown in the label of the date input. Required to have a date input option.
@param object: The object to bind the date input to. Required to enbale date input!!
-->

<template>
  <transition name="modal">
    <div class="modal-mask"  transition="modal" id="modalMask">
      <div class="modal-wrapper" id="modalWrapper">
        <div class="modal-container" id="modalContainer">
          <div class="modal-header" id="modalHeader">
            <button type="button" id="closebtn" class="close" @click="$emit('close')" ><i class="fa fa-times" aria-hidden="true"></i></button>
            <h3> {{modalHeaderTitle}} </h3>
          </div>

          <div class="modal-body" id="modalBody">
            <p> {{modalBodyText}}</p>
            <div>
        <date-input-form-group v-if="object != null"
                    :object="object" name="endDate" :text="endDate" :rules="'required'">
        </date-input-form-group>
        </div>
          </div>

          <div class="modal-footer " id="modalFooter" >
              <!--<button v-if="optionalButtonText" type="button" class="modal-default-button btn" @click="$emit('optional')">  {{ optionalButtonText }}  </button>-->
              <button v-if="cancelButtonText" type="button" class="modal-default-button btn" @click="$emit('cancelModal')"> {{ cancelButtonText}}  </button>
              <button v-if="confirmButtonText" type="button" class="modal-default-button btn" @click="$emit('confirmModal')">  {{ confirmButtonText }}  </button>
    
          </div>

        </div>
      </div>
    </div>
  </transition>
</template>
<script>
import DateInputFormGroup from '../form/FormGroups/DateInputFormGroup.vue'

 export default {
   props: {
     confirmButtonText: String, // Shown text on confirmation button
     cancelButtonText: String, // Shown text on cancel button
     optionalButtonText: String, // Shown text on optional button
     modalHeaderTitle: String, // Shown text on modal header
     modalBodyText: String, // shown text on modal body
     endDate: String, // Shown text on date input
     object: Object // Object to bind date input to
   },
   components: {
     DateInputFormGroup
   },
   mounted(){
     this.$parent.$emit('mounted', this.$children)
   },

 }
</script>

<style>

#modalMask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .6);
  display: table;
  transition: opacity .3s ease;
  /* new */ 
  text-align: center;
}

#modalWrapper {
  display: table-cell;
  vertical-align: middle;
}

#modalContainer {
  width: 30%;
  margin: 0px auto;
  /*padding: 20px 30px;*/
  background: #304052;
  /* make border radius work */
  border-radius: 7px;
  border-bottom: 10px solid white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  overflow: hidden;
}

#modalHeader h3 {
  margin-top: 1.5%;
  color: #1AB394;
}

#modalBody p{
  color:#304052;
  font-size:18px;
  margin-top:4%;
  font-weight: 600;

 }

#modalFooter{
  background: #ffffff;
  border:none;

 }

#modalBody{
  background-color: #ffffff;
}

    #modalFooter button{
        background:#1AB394;
        color:white;
        width: 100px;
        margin: 0px 10px 0px 10px;
        font-weight: 600;
    }

    #modalFooter button:hover{
        background:#009D7E;
        color:white;
    }


.modal-default-button {
  float: right;
  /* styling */
  background:#1AB394;
  color:#ffffff;

}

/* Transition effects*/

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

/* responsive */

@media (max-width: 990px) {
  #modalContainer {
    width:55%;
  }
}
/* Tablet support */
@media (max-width: 764px) {
  #modalContainer {
    width:75%;
  }
}
/* Mobile support (support until 480x320 and 320x480 )*/

@media (max-width: 540px) {

  #modalContainer {
    width:70%;
  }

  #modalFooter button{
    width: 35%;
    margin:10px;
  }

  #modalHeader h3 {
    font-size:17px;
  }

  #modalBody p{
    font-size:15px;

   }
}
</style>