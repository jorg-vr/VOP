<!--
    Component usable for letting the user select the language of the website.
    TODO: Make the language persistent on page reloads.
    TODO: language flag is not showing
-->
<template>
    <!-- Wrap select into <p> to be able to add language flag -->
     <p> 
        <span class="lang-sm" v-bind:lang="currentLangCode"></span>
        <select class="picker form-control" :value="currentLangName" @change="updateLanguage($event.target.value)" >
            <option v-for="lang in languages" :selected="lang.code===currentLangCode">{{lang.name}}</option>
        </select> 
    </p>
</template>

<script>
    import Vue from 'vue';
    import {mapMutations} from 'vuex'

    export default {
        data() {
            return {
                lang: Vue.config.lang,
                languages: [
                    {
                        code: 'nl',
                        name: 'Nederlands'
                    },
                    {
                        code: 'en',
                        name: 'English'
                    }
                ]
            }
        },
        computed: {
            currentLangCode(){
                return Vue.config.lang
            },
            currentLangName(){
                let lang = this.languages.filter(lang => lang.code === this.currentLangCode)[0]
                return lang.name
            }
        },
        methods: {
            ...mapMutations([
                'setLanguage'
            ]),
            updateLanguage(name){
                let language = this.languages.filter(lang => lang.name === name)[0]
                this.setLanguage({language: language.code})
            }
        }
    }
</script>
<style>
     .picker {
     .picker {
         margin-top: 8px;
     }
    .lang-sm{
        margin-top: 5px;
    }
 option{
        color:black;
    }
</style>
