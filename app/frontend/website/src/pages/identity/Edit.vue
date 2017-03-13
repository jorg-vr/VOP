<template>
        <div id="content-wrapper">
               <div class="page-header">    
             <h1> {{ this.$route.query.type }} aanpassen: {{ this.$route.query.user.name }} </h1>
        </div>  
        <form-temp v-bind:msg=this.$route.query.type></form-temp>
            <router-view></router-view>
        <div class="row">
            <div class="col-md-4 col-md-offset-7">
                <div id="buttons">
                <button type="button" class="btn btn-success btn-md" v-on:click="proceed(true)"> <i class="fa fa-check" aria-hidden="true"></i></button>    
                <button type="button" class="btn btn-danger btn-md" v-on:click="proceed(false)"> <i class="fa fa-times" aria-hidden="true"></i></button>  
                </div>
            </div>
        </div>

    </div>
</template>
<script>
    import FormTemp from './Form.vue'
    export default {
    data(){
        return {
        }
        },
        components: { FormTemp},
        methods:{
                proceed: function (cond) {
                            if(cond){
                                alert('Gebruiker gewijzigd!')
                                // AJAX POST MESSAGE NAAR API
                            }
                            else{
                                alert('Geannuleerd!')
                            }
                           this.$router.go(-1)
                },
                determineForm: function () {
                  
                },
                // call on load
                mounted() {
                    
                }
        }
    }
</script>