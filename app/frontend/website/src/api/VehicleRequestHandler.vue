<script>
    import RequestHandler from './RequestHandler.vue'

    export default {
        data(){
            return {
                vehiclePath: 'vehicles/',
                /* Data returned from API calls are available in these attributes */
                vehicles: [],
                vehicleTypes: [],
                fetchedVehicles: false,
                fetchedTypes: false
            }
        },
        mixins: [RequestHandler],
        methods : {

            /*
             API call to fetch all fleets. The fleets will be saved in the fleets variable.
             */
            fetchVehicles (query, afterFunction){
                this.get(this.vehiclePath + '?' + query).then(response => {
                        this.vehicles = response.body.data;
                        this.fetchedVehicles = true
                        if(afterFunction){
                            afterFunction();
                        }
                    }
                )
            },

            /*
             API call to delete a vehicle.
             This function requires a vehicle, not only an ID!
             */
            deleteVehicle(vehicle){
                this.delete(this.vehiclePath + vehicle.id);
                //Remove the vehicle locally.
                this.vehicles = this.vehicles.filter(fleet => fleet.id !== id);
                let subfleet = this.subfleets.find(subfleet => subfleet.type.id === vehicle.type);
                subfleet = subfleet.filter(vehicle => vehicle.id !== id)
            },

            /*
             API call to fetch all types of vehicles.
             */
            fetchVehicleTypes(afterFunction){
                this.get('vehicleTypes/').then(response => {
                    this.vehicleTypes = response.body.data;
                    this.fetchedTypes = true
                    if(afterFunction){
                        afterFunction();
                    }
                });
            }
        }
    }
</script>