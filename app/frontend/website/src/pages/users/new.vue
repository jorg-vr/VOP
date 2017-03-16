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
            this.$bus.$on('proceedAdd', input => this.createUser(input));
            // API call to create a new user in the database.
        },
        methods: {
            createUser(user){
                this.$http.post('https://vopro5.ugent.be/app/api/users', user,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        this.$router.push({name: 'user', params: {id: response.body.id}});
                    }, response => { //Fail
                        console.log('fail')
                    }
                );
            }
        }
    }
</script>

