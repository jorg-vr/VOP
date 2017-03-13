<template>
    <div>
        <div class="page-header">
            <h1>Nieuw subvloot</h1>
        </div>
        <div class="row">
            <div class="col-md-4 form-group">
                <input type="text" class="form-control input-sm" v-model="vehicle_type" placeholder="Type voertuig">
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
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
                vehicle_type: ''
            }
        },
        methods: {
            proceed: function (cond) {
                if(cond){
                    //HTTP request
                }
                else{
                    //Errors
                }
                //Temporary
                this.$router.go(-1);
            }
        }
    }
</script>