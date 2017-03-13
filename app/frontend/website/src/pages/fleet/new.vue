<template>
    <div>
        <div class="page-header">
            <h1>Nieuwe vloot</h1>
        </div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm" v-model="email" placeholder="E-mail klant">
            </div>
        </div>
        <div class="row">
            <div>
                <div id="buttons">
                    <button type="button" class="btn btn-success btn-md" v-on:click="proceed(true)"> <i class="fa fa-check" aria-hidden="true"></i></button>
                    <button type="button" class="btn btn-danger btn-md" v-on:click="proceed(false)"> <i class="fa fa-times" aria-hidden="true"></i></button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                email: ''
            }
        },
        methods: {
            proceed: function (cond) {
                if(cond){
                    alert('Not implemented yet, returning to fleets page.');
                    this.$http.get('https://vopro5.ugent.be/app/api/users?email=' + this.email).then(response => {
                        //Check if the user is actually a client
                       //Get ID from query and create new fleet with as parameter the client ID
                    });
                    //this.$router.push({name: 'fleet', params: { id: 1 }})
                }
                else{
                    //this.$router.go(-1);
                }
                //Temporary
                this.$router.go(-1);
            }
        }
    }
</script>
