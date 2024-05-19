<template>
  <v-container>
    <v-row class="mb-4">
      <!-- Card for LocalHost -->
      <v-col cols="12">
        <v-card>
          <v-card-title class="text-center">LocalHost URL</v-card-title>
          <v-card-text class="text-center">
            <v-text-field
              v-model="localHost"
              label="LocalHost URL"
              class="mx-auto"
              max-width="500"
            ></v-text-field>
            <v-btn small variant="outlined" @click="setLocalHost"
              >Set LocalHost URL</v-btn
            >
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import service from "@/services/service";

// ******************************************
// Initialization
// ******************************************

// Reactive references
const localHost = ref("http://localhost:8080");

// ******************************************
// Methods
// ******************************************

// Method to change the API url to local host
const setLocalHost = () => {
  service.setBaseUrl(localHost.value);
};
</script>
