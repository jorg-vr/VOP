<!--
    Component usable for letting the user select his function.
    Once a function is chosen the page will reload and the user will be redirected to the home page in order to reset state.
-->
<template>
    <form-select selectClass="picker" optionKey="roleName" :options="userFunctions"
                 @input="updateActiveFunction(accountFunction.id)"
                 v-model="accountFunction.id"></form-select>
</template>
<script>
    import formSelect from '../form/elements/formSelect.vue'
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        data(){
            return {
                accountFunction: {}
            }
        },
        created(){
            if(this.userFunctions.length === 0){
                this.fetchUserFunctions()
            }
            this.accountFunction = this.activeFunction
        },
        components: {
            formSelect
        },
        computed: {
            ...mapGetters([
                'userFunctions',
                'activeFunction'
            ]),
        },
        methods: {
            ...mapActions([
                'setActiveFunction',
                'fetchUserFunctions'
            ]),
            updateActiveFunction(functionId){
                let userFunction = this.userFunctions.filter(obj => obj.id === functionId)[0]
                this.setActiveFunction(userFunction)
                //Navigate back to home once the function has changed.
                //This method is used, and not the router, as the page has to reload in order to remove unwanted state
                document.location.href = "/"
            }
        }
    }
</script>
