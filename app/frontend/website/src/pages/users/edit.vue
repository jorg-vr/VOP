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
        methods:{
            // Function that makes an API call to fetch specific user data to be edited
            fetchUser : function () {
                this.$http.get('https://vopro5.ugent.be/app/api/users/' + this.$route.params.id).then(response => {
                    this.user = response.body;
                })
            },
            editUser(user) {
                this.$http.put('https://vopro5.ugent.be/app/api/users/' + this.$route.params.id, user,
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
            }


        },
        created: function (){
            // Keep reference to this Vue component
            this.fetchUser()
            // Listen to proceedEdit performed by child component (userform.vue)
            this.$bus.$on('proceedEdit', input => this.editUser(input));
        }
    }




</script>