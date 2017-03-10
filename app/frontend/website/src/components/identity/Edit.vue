<template>
        <div id="content-wrapper">
               <div class="page-header">
             <h1> {{ this.$route.query.type }} aanpassen voor {{this.$route.params.user}}</h1>
        </div>  
        <form-temp></form-temp>
            <router-view></router-view>

        <button type="button" class="btn btn-primary btn-md" v-on:click="proceed(true)">BEVESTIG</button>    
        <button type="button" class="btn btn-primary btn-md" v-on:click="proceed(false)">ANNULEER</button>  


    </div>
</template>
<script>
    import FormTemp from './Form.vue'
    export default {
    data(){
        return {
            identityType: this.$route.query.type 
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