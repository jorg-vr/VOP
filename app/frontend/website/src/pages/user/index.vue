<!--
    This page shows all users in the database.
    From this page a new user can be added or an existing user can be edited or removed.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Gebruikers</h1>
        </div>
        <!-- Render an info-pane for every user -->
        <info-pane v-for="users in userList"
                   :textValues="new Array(users.firstName,users.lastName)"
                   :remove="remove"
                   :objectId= "users.id"
                   edit="edit_user"
                   show="user"
                   :key="users.id">
        </info-pane>

        <button type="button" class="btn btn-primary btn-circle btn-lg" v-on:click="add()">+</button>

    </div>



</template>
<script>
    import infoPane from "../../assets/listComponent.vue"
    export default {
        components: {
            'info-pane': infoPane
        },
        data(){
            return {
                userList : [],
                users: {},
                search: ''
            }
        },
        methods:{
            // Methods for routing purposes
            // Route to adding a new user
            add: function () {
                this.$router.push({ path: 'users/new'})
            },
            // Function to remove a user 
            remove : function (id){
                // API call to remove user from database
                this.$http.delete('https://vopro5.ugent.be/app/api/users/'+id)
                let newUsers = this.userList.filter(user => user.id !== id);
                this.userList = newUsers
            },
            // Function to fetch list of users from database
            fetchUserList(){
                this.$http.get('https://vopro5.ugent.be/app/api/users').then(response => {
                        const data = response.body.data;
                        for (let i = 0; i < data.length; i++) {
                            this.userList.push(data[i]);
                        }
                    }, response => {
                        console.log('fail');
                    }
                 )
            }
        },
        computed: {
            // Computed property used for searching
            searchId: function () {
                var s=this.search.trim().toLowerCase();;
                var listID = []
                listID = this.users
                if(!this.search){
                    return listID
                }
                else{
                    console.log(listID)
                    return listID.filter(function(number){
                        if(number.name.toLowerCase().indexOf(s) != -1){
                            return number
                        }
                    })
                }
            },
            // Computed property used as a placeholder in the searchbar
            appendString : function (){
                var value = 'Gebruiker zoeken'
                return value
            },
        },
        // Lifecycle hook called when this component is created
        created: function () {
            this.fetchUserList()
         
        },
    }
</script>
<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
    }
</style>