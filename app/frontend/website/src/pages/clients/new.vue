<!--
    This page is used to create a new client based on userinput for a name, an address, a VAT number and a phone number.
-->
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
                // Data used to fill in form placeholders for a client
                data: ["Naam","Land","Plaats","Postcode","Straat","Nummer","BTW nummer","Telefoonnummer"],
                type: "Klant"
            }
        },
        components: { FormTemp},
        created: function (){
            // Keep reference to this Vue component 
            var vm = this
            // Listen to proceedAddClient performed by child component (clientform.Vue)
            this.$bus.$on('proceedAddClient', function(input){
                // API call to create a new client in the database.
                this.$http.post('https://vopro5.ugent.be/app/api/companies', input,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        console.log('success')
                        console.log(response.body);
                    }, response => { //Fail
                        console.log('fail')
                        console.log(response)
                    }
                )
            });
        }
    }  
</script>

