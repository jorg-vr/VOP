<!--
    Based on https://gist.github.com/martinlindhe/f34489126eb635cb241f
-->
<template>
    <select id="language-picker" class="form-control" :value="currentLangName" @change="updateLanguage($event.target.value)">
        <option v-for="lang in languages" :selected="lang.code===currentLangCode">{{lang.name}}</option>
    </select>
</template>

<script>
    import Vue from 'vue';
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
            updateLanguage(name){
                let lang = this.languages.filter(lang => lang.name === name)[0]
                Vue.config.lang = lang.code
            }
        }
    }
</script>
<style>
    #language-picker {
        margin-left: 10px;
    }
</style>