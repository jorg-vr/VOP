<template>
    <div>
        <div class="page-header">
            <h1>Nieuw subvloot</h1>
        </div>
        <div class="row">
            <div class="col-md-4 form-group">
                <input type="text" class="form-control input-sm" v-model="subfleet.vehicle_type" placeholder="Type voertuig">
            </div>
        </div>

        <p>Toon zoekfunctie om voertuig te zoeken (zie klant zoeken: zie /identities?type=Klant (Dit modulariseren?)))
            Voer vervolgens HTTP Post request uit en ga naar pagina van huidige vloot
        </p>
        <button v-on:click="createFleet(1)" class="btn btn-primary">Bevestig</button>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                subfleet: {
                    vehicle_type : ''
                }
            }
        },
        methods: {
            createFleet (clientID){
                this.$http.post('' + clientID).then(response => {
                    //Verwerk response

                })
                this.$router.push({name: 'fleet', params: { id: 1 }})
            }
        }
    }
</script>