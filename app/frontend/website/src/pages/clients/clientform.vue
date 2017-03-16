<!--
    This page is used to generate a form for a client.
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
                        <router-link :to="{name: 'clients'}">  
                        <button type="button" class="btn btn-success btn-md" v-on:click="proceed()"> <i class="fa fa-check" aria-hidden="true"></i></button> 
                        </router-link>
                        <router-link :to="{name: 'clients'}">   
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
        props: { at: Array, type: String, client: Object},
        data(){
            return {
                inputs: []
            }
        },
        methods: {
             // Function to create a client object needed for a post request
            createCompanyObject : function () {
                return {name:this.inputs[0],vatNumber:this.inputs[6],phoneNumber:this.inputs[7],address:{country:this.inputs[1],city:this.inputs[2],street:this.inputs[4],houseNumber:this.inputs[5],postalCode:this.inputs[3]}}
            },
            // Function that tells parent (edit.vue) to confirm the action
            proceed : function (){
                var dest=''
                if(this.$route.path == '/clients/new'){
                    var Company = this.createCompanyObject()
                    dest='proceedAddClient'
                    this.$bus.$emit(dest,Company)
                }
                else if(this.$route.path == '/clients/'+this.$route.params.id+'/edit'){
                    var Company = this.createCompanyObject()
                    dest='proceedEditClient'
                    this.$bus.$emit(dest,Company)
                }
            },
            fetchEditData(){
                this.$http.get('https://vopro5.ugent.be/app/api/companies/' + this.$route.params.id).then(response => {
                    var Client = response.body
                    var ad = Client.address
                    this.inputs.push(Client.name)
                    this.inputs.push(ad.country)
                    this.inputs.push(ad.city)
                    this.inputs.push(ad.postalCode)
                    this.inputs.push(ad.street)
                    this.inputs.push(ad.houseNumber)
                    this.inputs.push(Client.vatNumber)
                    this.inputs.push(Client.phoneNumber)
                })
            }
        },
        // Lifecycle hook called when this component is created
        created : function(){ 
            // Fill inputs array with received information
            if(this.$route.path != '/clients/new'){
                this.fetchEditData()
            }
        }
    }
</script>
<style scoped>

</style>