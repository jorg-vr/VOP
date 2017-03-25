<!--
    This is the form.vue for creating/updating a fleet.
    The form.vue accepts the old data and an update or create function.
-->
<template>
    <div>
        <form class="form-horizontal">
            <form-input placeholder="Naam vloot" :value="fleet.name"></form-input>
            <div class="form-group">
                <label class="col-sm-4 control-label">Bedrijf</label>
                <div class="col-sm-8">
                    <select class="form-control" v-model="fleet.company">
                        <option v-for="company in companies" v-bind:value="company.id">{{company.name}}</option>
                    </select>
                </div>
            </div>
        </form>
        <div class="row">
            <div>
                <div id="buttons">
                    <button class="btn btn-success btn-md" v-on:click="submit(fleet)">
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
    import formInput from '../../assets/form/formInput.vue'
    export default {
        data() {
            //The values for the fleet.
            return {
                companies: []
            }
        },
        components: {
            'form-input': formInput
        },
        created(){
            //this.fetchCompanies()
        },
        props: {
            fleet: Object,
            submit: Function //Function to create the fleet.
        },
        methods: {
            fetchCompanies(){
                this.$http.get('https://vopro5.ugent.be/app/api/companies').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.companies.push(data[i]);
                    }
                })
            }
        }
    }

</script>