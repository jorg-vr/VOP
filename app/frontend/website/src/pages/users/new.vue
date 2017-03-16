<!--
    This page is used to create a new user based on userinput for a first name, last name and email.
-->
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
                // Data used to fill in form placeholders for a user
                data: ["Voornaam","Achternaam","Email"],
                type: "Gebruiker"
            }
        },
        components: { FormTemp},
        created: function (){
            // Keep reference to this Vue component 
            var vm = this
            // Listen to proceed performed by child component (userform.Vue)
            this.$bus.$on('proceedAdd', function(input){
                // API call to create a new user in the database.
                this.$http.post('https://vopro5.ugent.be/app/api/users', input,
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

