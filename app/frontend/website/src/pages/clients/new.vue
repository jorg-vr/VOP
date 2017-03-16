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
        methods: {
            createClient(client){
                this.$http.post('https://vopro5.ugent.be/app/api/companies', client,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        this.$router.push({name: 'client', params: {id: response.body.id}});
                    }, response => { //Fail
                        console.log('fail')
                        console.log(response)
                    }
                )
            }
        },
        created: function (){
            // Listen to proceedAddClient performed by child component (clientform.Vue)
            this.$bus.$on('proceedAddClient', input => this.createClient(input));
        }
    }  
</script>

