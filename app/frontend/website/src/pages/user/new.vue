<!--
    This page is used to create a new user based on userinput for a first name, last name and email.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1> Gebruiker aanmaken </h1>
        </div>
        <form-temp :user="{}" :submit=createUser></form-temp>
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
            createUser(user){
                console.log('check')
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

