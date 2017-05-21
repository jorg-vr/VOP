<!--
    Component usable for letting the user select his function.
    Once a function is chosen the page will reload and the user will be redirected to the home page in order to reset state.
-->
<template>
    <li v-if="userFunctions.length > 1" class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" ariahaspopup="true" aria-expanded="false">
            {{activeFunction.roleName}} <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
            <li>
                <a class="no-hover">{{$t('actions.change', {subject: $t('user.role')}) | capitalize}}</a>
            </li>
            <li role="separator" class="divider"></li>
            <li v-for="userFunction in userFunctions">
                <a v-if="userFunction.id != activeFunction.id" @click="onInput(userFunction)" class="pointer">
                    {{userFunction.roleName}}
                </a>
            </li>
        </ul>
    </li>

</template>
<script>
    import formSelect from '../form/FormGroups/SelectInputFormGroup.vue'
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        data(){
            return {
                accountFunction: {}
            }
        },
        created(){
            if(this.userFunctions.length === 0){
                this.fetchUserFunctions();
            }
            this.accountFunction = this.activeFunction;
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
            onInput(userFunction){
                this.setActiveFunction(userFunction);
                //Navigate back to home once the function has changed.
                //This method is used, and not the router, as the page has to reload in order to remove unwanted state
                location.reload();
                this.$router.push({name:'homeClient'})
            }
        }
    }
</script>
