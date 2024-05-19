<template>
  <v-app>
    <AppBar />
    <v-main>
      <router-view></router-view>
    </v-main>
    <AppFooter />
  </v-app>
</template>

<script setup>
import service from "@/services/service";
// ******************************************
// Initialization
// ******************************************
const localHost = "http://localhost:8080";
// Set default API url when the app is loaded or refreshed
service.setBaseUrl(localHost);
</script>
