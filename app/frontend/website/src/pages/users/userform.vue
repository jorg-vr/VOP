<!--
    This page is used to generate a form for a user.
-->
<template>
    <div>
        <form>  
            <div class="row" v-for="(a,v) in this.at">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" name="fname" id="fname" class="form-control input-sm" v-bind:placeholder=a v-model=inputs[v]>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-7">
                    <div id="buttons">
                        <router-link :to="{name: 'users'}">  
                        <button type="button" class="btn btn-success btn-md" v-on:click="proceed()"> <i class="fa fa-check" aria-hidden="true"></i></button> 
                        </router-link>
                        <router-link :to="{name: 'users'}">   
                          <button type="button" class="btn btn-danger btn-md"> <i class="fa fa-times" aria-hidden="true"></i></button>  
                        </router-link>
                    </div>
                </div>
            </div>
        </form>

    </div>
</template>
<script>
    export default {
        props: { at: Array, type: String, user: Object},
        data(){
            return {
                inputs: []
            }
        },
        methods: {
            // Function to create a User object needed for a post request
            createUserObject : function () {
                return {firstName:this.inputs[0],lastName:this.inputs[1],email:this.inputs[2]}
            },
            // Function that tells parent (edit.vue) to confirm the action
            proceed : function (){
                var dest=''
                if(this.$route.path == '/users/new'){
                    var User = this.createUserObject()
                    dest='proceedAdd'
                    this.$bus.$emit(dest,User)
                }
                else if(this.$route.path == '/users/'+this.$route.params.id+'/edit'){
                    var User = this.createUserObject()
                    dest='proceedEdit'
                    this.$bus.$emit(dest,User)
                }

  
            },


        },
        // Lifecycle hook called when this component is created
        created : function(){ 
            // Fill inputs array with received information
            if(this.$route.path != '/users/new'){
                console.log(this.user)
                var fname = this.user.firstName
                var lname = this.user.lastName
                var email = this.user.email
                this.inputs.push(fname);
                this.inputs.push(lname);
                this.inputs.push(email);
                console.log(this.inputs)
            }

        }
    }
</script>
<style scoped>

</style>