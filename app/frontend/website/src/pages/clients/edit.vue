<!--
    This page is used to edit a certain client.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1> Klant aanpassen </h1>
        </div>
        <form-temp :client=client :submit="updateClient"></form-temp>
    </div>
</template>
<script>
    import FormTemp from './clientform.vue'
    export default {
        data(){
            return {
                client:{}
            }
        },
        components: { FormTemp},
        methods:{
            // Function that makes an API call to fetch specific client data to be edited
            fetchClient : function () {
                this.$http.get('https://vopro5.ugent.be/app/api/companies/' + this.$route.params.id).then(response => {
                    this.client = response.body;
                    console.log(this.client)
                })
            },
            updateClient(client){
                this.$http.put('https://vopro5.ugent.be/app/api/companies/' + this.$route.params.id, client,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Succes
                        this.$router.push({name: 'client', params: {id: response.body.id}});
                    }, response => { //Fail
                        console.log('fail')
                    }
                )
            }


        },
        created: function (){
            this.fetchClient()
        }
    }




</script>