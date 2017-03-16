<!--
    This page is used to edit a certain user.
-->
<template>
    <div id="content-wrapper">
       <div class="page-header">    
           <h1> Gebruiker aanpassen </h1>
       </div>  
       <form-temp :at=this.data :type=this.type :user=this.user></form-temp>
   </div>
</template>
<script>
    import FormTemp from './userform.vue'
    export default {
        data(){
            return {
                user:{},
                data: ["Voornaam","Achternaam","Email"],
                type: "Gebruiker"
            }
        },
        components: { FormTemp},
        created: function (){
                // Keep reference to this Vue component 
                var vm = this
                var id = this.$route.params.id

                this.fetchUser()
                
                // Listen to proceedEdit performed by child component (userform.vue)
                this.$bus.$on('proceedEdit', function(input){
                    // API call to update information for a user
                    this.$http.put('https://vopro5.ugent.be/app/api/users/' + id, input,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                    ).then(response => { //Succes
                            this.$router.push({name: 'user', params: {id: response.body.id}});
                        }, response => { //Fail
                        console.log('fail')
                    }
                    )
                    
                });

            }
        }




    </script>