<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1> Gebruiker aanmaken </h1>
     </div>  
     <form-temp :at=this.data :type=this.type></form-temp>
 </div>
</template>
<script>
    import FormTemp from './userform.vue'
    export default {
        data(){
            return {
                data: ["Voornaam","Achternaam","Email"],
                type: "Gebruiker"
            }
        },
        components: { FormTemp},
        created: function (){
            // Keep reference to this Vue component 
            var vm = this
            // Listen to proceed performed by child component (Form.Vue)
            this.$bus.$on('proceedAdd', function(input){
                console.log(input)

                console.log('POST REQUEST NAAR https://vopro5.ugent.be/app/api/users/'+input)
                // this.$http.post('https://vopro5.ugent.be/app/api/users', input, {

                // })
                });}
        }  
    </script>

