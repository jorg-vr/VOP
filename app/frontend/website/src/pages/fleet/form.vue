<template>
    <div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm"
                       placeholder="E-mail klant" :value="this.currentEmail" v-model="newEmail">
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <input type="text" class="form-control input-sm"
                       placeholder="Naam vloot" :value="currentName" v-model="newName">
            </div>
        </div>
        <div class="row">
            <div>
                <div id="buttons">
                    <button class="btn btn-success btn-md" v-on:click="submit">
                        <i class="fa fa-check" aria-hidden="true"></i>
                    </button>
                    <router-link :to="{name: 'fleets'}">
                        <button type="button" class="btn btn-danger btn-md">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </button>
                    </router-link>

                </div>
            </div>
        </div>
    </div>

</template>
<script>
    export default {
        data() {
            return {
                newEmail: '',
                newName: ''
            }
        },
        props: {
            currentEmail: String,
            currentName: String
        },
        methods: {
            submit(){
                this.$emit('formSubmitted', newEmail, newName)
            }
        }
    }

</script>