<template>
    <div>
        <h1>Nieuw vloot</h1>
        <p>Kies de klant:</p>
        <p>Toon zoekfunctie om klant te zoeken: zie /identities?type=Klant (Dit modulariseren?).
            Voer vervolgens HTTP Post request uit en ga naar pagina van vloot met nieuwe ID.
        </p>
        <router-link :to="{name: 'fleet', params: { id:1 }}">
            <button class="btn btn-primary">Bevestig</button>
        </router-link>
        <p></p>
    </div>
</template>
<script>
    export default {
        data(){
            return {}
        },
        methods:{

        }
    }
</script>