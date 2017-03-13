<template>
    <div id="content-wrapper">
     <div class="page-header">    
     <h1> Gebruiker aanpassen</h1>
   </div>  
   <form-temp :at=this.data :type=this.type></form-temp>
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
                console.log('creating edit.vue')
                // listen to proceed performed by child component
                var vm = this

                this.$bus.$on('proceedEdit', function(input){
                    console.log(input)
                    console.log('proceedEdit called')
                    // Determine type
                    console.log("Gebruiker aangepast")
                    // Make post request for user
                    console.log('https://vopro5.ugent.be/app/api/users/'+input)
                    this.$http.put('https://vopro5.ugent.be/app/api/users/'+this.$route.query.id).then(response => {
                        
                    })
                    vm.$router.go(-1)
                });


                // get specific data to edit identity
        
                console.log('https://vopro5.ugent.be/app/api/users/'+this.$route.query.id )
                this.$http.get('https://vopro5.ugent.be/app/api/users/'+this.$route.query.id).then(response => {
                        // fire event that will send specific data to child component (Form)
                        this.$bus.$emit('getEditInfo',response.body,"Gebruiker")
                })
            }
        }

                

            
</script>