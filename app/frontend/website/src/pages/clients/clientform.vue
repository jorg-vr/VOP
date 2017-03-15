<template>
    <div>
        <form>  
            <div class="row" v-for="(a,v) in this.at">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" name="fname" id="fname" class="form-control input-sm" v-bind:placeholder=a v-bind:hidden=v v-model=inputs[v]>
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
        props: { at: Array, type: String },
        data(){
            return {
                inputs: []
            }
        },
        methods: {
        },
        created: function () {
        },
        methods: {
            createCompanyObject : function () {
                return {name:this.inputs[0],vatNumber:this.inputs[6],phoneNumber:this.inputs[7],address:{country:this.inputs[1],city:this.inputs[2],street:this.inputs[4],houseNumber:this.inputs[5],postalCode:this.inputs[3]}}
            },
            proceed : function (){

                var dest=''
                console.log(this.inputs)
                console.log('Child (Form) fired proceed method')
                if(this.$route.path == '/clients/new'){
                    console.log('Go to proceed Add')
                    var Company = this.createCompanyObject()
                    dest='proceedAddClient'
                    this.$bus.$emit(dest,Company)
                }
                else if(this.$route.path == '/clients/'+this.$route.params.id+'/edit'){
                    console.log('Go to edit Add')
                    var Company = this.createCompanyObject()
                    dest='proceedEditClient'
                    this.$bus.$emit(dest,Company)
                }

  
            },


        },
        mounted : function(){ 
            var vm = this;
            // listen to event fired by Edit Vue
            this.$bus.$on('getEditInfo', function(input,type){
                //TODO

            });

        }
    }
</script>
<style scoped>

</style>