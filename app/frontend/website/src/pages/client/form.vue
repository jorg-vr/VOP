<!--
    This page is used to generatfleetForm.vue.vue for a client.
-->
<template>
    <div>
        <form>  
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Naam" v-model="client.name">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Land" v-model="client.address.country">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Plaats" v-model="client.address.city">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Postcode" v-model="client.address.postalCode">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Straat" v-model="client.address.street">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Nummer" v-model="client.address.houseNumber">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="BTW nummer" v-model="client.vatNumber">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Telefoonnummer" v-model="client.phoneNumber">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-7">
                    <div id="buttons">
                        <button type="button" class="btn btn-success btn-md" v-on:click="submit(client)"> <i class="fa fa-check" aria-hidden="true"></i></button>
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
        props: {
            client: Object,
            submit: Function
        },
        data(){
            return {
                inputs: []
            }
        }
    }
</script>