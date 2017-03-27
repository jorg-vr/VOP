<template>
    <div>
        <div class="well-searchbox">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-md-4 control-label">Keyword</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" placeholder="Keyword">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Country</label>
                    <div class="col-md-8">
                        <select class="form-control" placeholder="Country">
                            <option value="">All</option>
                            <option value="">Country 1</option>
                            <option value="">Country 2</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Province</label>
                    <div class="col-md-8">
                        <select class="form-control" placeholder="Province">
                            <option value="">All</option>
                            <option value="">Province 1</option>
                            <option value="">Province 2</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">City</label>
                    <div class="col-md-8">
                        <select class="form-control" placeholder="City">
                            <option value="">All</option>
                            <option value="">City 1</option>
                            <option value="">City 2</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Category</label>
                    <div class="col-md-8">
                        <select class="form-control" placeholder="Category">
                            <option value="">All</option>
                            <option value="">Category 1</option>
                            <option value="">Category 2</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-offset-4 col-sm-5">
                    <button type="submit" class="btn btn-success">Search</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            /*
             This is an array of objects. Each object should have a filtername and filterfunction
             */
            filters: Array
        }
    }
</script>
<style>
    .search-background img {
        width: 100%;
    }
    .well-searchbox {
        min-height: 20px;
        min-width: 400px;
        padding: 19px;
        position: absolute;
        z-index: 80;
        top: 90px;
        right: 100px;
        background: rgba(0, 0, 0, 0.6);
        margin-bottom: 20px;
        border: 1px solid #e3e3e3;
        border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    }

    .well-searchbox label {
        color: #fff;
    }
</style>