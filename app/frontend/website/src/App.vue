<template>
  <div id="content-wrapper">

    <!-- Sidebar -->
    <div id="sidebar-wrapper">
      <ul class="sidebar-nav">
        <li class="sidebar-brand">Solvas fleet</li>
        <li><a href="#">Vloten</a></li>
        <li><a href="#">Subvloten</a></li>
        <li><a href="#">Voertuigen</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Services</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
    </div>

    <div class="container-fluid">
      <div class="col-sm-3">

      </div>
      <div class="col-sm-9">
        <router-view></router-view>
      </div>
    </div>

  </div>
</template>

