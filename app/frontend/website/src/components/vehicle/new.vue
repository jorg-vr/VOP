<template>
    <div>
        <div class="page-header">
            <h1>Voeg een nieuw voertuig toe</h1>
        </div>

        <!-- form -->
        <form class="form-horizontal col-md-6">
            <div class="form-group">
                <label class="col-sm-4 control-label">Merk</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Merk">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Model</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Model">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Type</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Type">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Nummerplaat</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Nummerplaat">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Chassisnummer</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Chassisnummer">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Productiedatum</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Productiedatum">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Waarde</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Waarde">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Kilometerafstand</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Kilometerafstand">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Bedrijf</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Bedrijf">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">Leasing bedrijf</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Leasing bedrijf">
                </div>
            </div>
            <button type="submit" class="btn btn-default float-left">Voeg toe</button>
        </form>
    </div>
</template>

<script>
    export default {
        data: function(){
            return {
                vehicle: {
                    brand: '',
                    model: '',
                    type: '',
                    license_plate: '',
                    chassis_number: '',
                    year: '',
                    kilometer_count: '',
                    leasing_company: ''
                }
            }
        },
        methods: {
            addVehicle: function(){
                this.$http.post('/vehicles', vehicle)
            }
        }
    }
</script>