
<script>
    export default {
        mixins: [require('../SearchBar.vue')],
        data() {
            return {}
        },
        props: {
            placeholder: {
                default: 'vloot'
            },
            options: {
                default: function () {
                    return [
                        {text: 'voot ID', value: 'fleet_id'},
                        {text: 'bedrijf', value: 'company'}
                    ]
                }
            },
            fleets: {
                default: function () {
                    return []
                },
                type: Array
            }

        },
        methods: {
            add() {
                this.$router.push({name: 'new_fleet'})
            },
            search: function () {
                const searchQuery = this.getSearchQuery;
                const selected = this.getSelectedOption;
                let filteredFleets = this.fleets;
                if(searchQuery != '') {
                    if(selected == 'fleet_id'){
                        filteredFleets = this.fleets.filter(function (fleet) {
                            if(fleet.id.indexOf(searchQuery) != -1){
                                return fleet;
                            }
                        });
                    }
                    else if(selected == 'company'){
                        filteredFleets =  this.fleets.filter(function (fleet) {
                            if(fleet.company.toLowerCase().indexOf(searchQuery) != -1){
                                return fleet;
                            }
                        });
                    }
                    else {
                        alert('Selecteer een zoekfunctie.'); //TODO
                    }
                }
                this.$emit('fleetsChanged', filteredFleets);
            }
        }
    }
</script>