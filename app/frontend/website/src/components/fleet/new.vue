<template>
    <div>
        <h1>Nieuw vloot</h1>
        <p>Kies de klant:</p>
        <p>Toon zoekfunctie om klant te zoeken: zie /identities?type=Klant (Dit modulariseren?).
            Voer vervolgens HTTP Post request uit en ga naar pagina van vloot met nieuwe ID.
        </p>
        <button v-on:click="createFleet(1)" class="btn btn-primary">Bevestig</button>
    </div>
</template>
<script>
    export default {
        data(){
            return {}
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