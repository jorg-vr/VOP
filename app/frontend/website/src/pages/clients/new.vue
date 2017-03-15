<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1> Klant aanmaken </h1>
     </div>  
     <form-temp :at=this.data :type=this.type></form-temp>
 </div>
</template>
<script>
    import FormTemp from './clientform.vue'
    export default {
        data(){
            return {
                data: ["Naam","Land","Plaats","Postcode","Straat","Nummer","BTW nummer","Telefoonnummer"],
                type: "Klant"
            }
        },
        components: { FormTemp},
        created: function (){
            // Keep reference to this Vue component 
            var vm = this
            // Listen to proceed performed by child component (Form.Vue)
            this.$bus.$on('proceedAddClient', function(input){
                console.log(input)
                console.log('proceedAddClient called')

                console.log('https://vopro5.ugent.be/app/api/companies/'+input)
                // this.$http.post('https://vopro5.ugent.be/app/api/companies', input, {

                // })
                });}
        }  
    </script>

