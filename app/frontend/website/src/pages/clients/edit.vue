<template>
    <div id="content-wrapper">
     <div class="page-header">    
     <h1> Klant aanpassen</h1>
   </div>  
   <form-temp :at=this.data :type=this.type></form-temp>
</div>
</template>
<script>
    import FormTemp from './clientform.vue'
    export default {
        data(){
            return {
                user:{},
                data: ["Naam","Land","Plaats","Postcode","Straat","Nummer","BTW nummer","Telefoonnummer"],
                type: "Klant"
            }
        },
        components: { FormTemp},
        created: function (){
                console.log('creating edit.vue')
                // listen to proceed performed by child component
                var vm = this
                var id = this.$route.query.id
                console.log(id)
                this.$bus.$on('proceedEditClient', function(input){
                    console.log(input)
                    console.log('proceedEditClient called')
                    // Determine type
                    console.log("Klant aangepast")
                    // Make post request for user
                    console.log('https://vopro5.ugent.be/app/api/companies/'+input)
                    this.$http.put('https://vopro5.ugent.be/app/api/companies/'+id).then(response => {
                        
                    })
                    vm.$router.go(-1)
                });


                // get specific data to edit identity
        
                console.log('https://vopro5.ugent.be/app/api/companies/'+id )
                this.$http.get('https://vopro5.ugent.be/app/api/companies/'+id).then(response => {
                        // fire event that will send specific data to child component (Form)
                        this.$bus.$emit('getEditInfo',response.body,"Klant")
                })
            }
        }

                

            
</script>