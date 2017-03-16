<!--
    This page is used to create a new client based on userinput for a name, an address, a VAT number and a phone number.
-->
<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1> Klant aanmaken </h1>
     </div>  
     <form-temp :client={address:{}} :submit="createClient"></form-temp>
 </div>
</template>
<script>
    import FormTemp from './form.vue'

    export default {
        data(){
            return {}
        },
        components: { FormTemp},
        methods: {
            createClient(client){
                console.log(client);
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
        }
    }  
</script>

