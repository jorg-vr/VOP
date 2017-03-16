<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
     <div class="page-header">    
     <h1> Klant aanpassen </h1>
   </div>  
   <form-temp :at=this.data :type=this.type :client=this.client></form-temp>
</div>
</template>
<script>
    import FormTemp from './clientform.vue'
    export default {
        data(){
            return {
                client:{},
                data: ["Naam","Land","Plaats","Postcode","Straat","Nummer","BTW nummer","Telefoonnummer"],
                type: "Klant"
            }
        },
        components: { FormTemp},
        created: function (){
                // listen to proceed performed by child component
                var vm = this
                var id = this.$route.params.id

                this.fetchClient()
                // Listen to proceedEdit performed by child component (clientform.vue)
                this.$bus.$on('proceedEditClient', function(input){
                    // API call to update information for a client
                     this.$http.put('https://vopro5.ugent.be/app/api/companies/' + id, input,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                    ).then(response => { //Succes 
                    }, response => { //Fail
                        console.log('fail')
                    }
                    )
                });

            }
        }

                

            
</script>