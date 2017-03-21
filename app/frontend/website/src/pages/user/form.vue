<!--
    This page is used to generate a form for a user.
-->
<template>
    <div>
        <form>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Voornaam" v-model="user.firstName">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="Achternaam" v-model="user.lastName">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div class="form-group">
                        <input type="text" class="form-control input-sm" placeholder="E-mail" v-model="user.email">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-7">
                    <div id="buttons">
                        <button type="button" class="btn btn-success btn-md" v-on:click="submit(user)"> <i class="fa fa-check" aria-hidden="true"></i></button>
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
        props: {
            user: Object,
            submit: Function
        },
        data(){
            return {
                inputs: []
            }
        }
    }
</script>
<style scoped>

</style>