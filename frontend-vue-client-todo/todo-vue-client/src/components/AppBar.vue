<template>
  <!-- App Bar -->
  <v-app-bar app>
    <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
    <router-link to="/" class="router-link">
      <v-toolbar-title>Todo List App</v-toolbar-title>
    </router-link>
  </v-app-bar>
  <!-- Navigation Drawer -->
  <v-navigation-drawer app v-model="drawer">
    <v-list dense>
      <!-- Home nav link -->
      <v-list-item>
        <router-link to="/" class="router-link">
          <v-list-item-icon class="list-item-icon">
            <v-icon>mdi-home</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </router-link>
      </v-list-item>
      <!-- Todos nav link -->
      <v-list-item>
        <router-link to="/todos" class="router-link">
          <v-list-item-icon class="list-item-icon">
            <v-icon>mdi-format-list-bulleted</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Todos</v-list-item-title>
          </v-list-item-content>
        </router-link>
      </v-list-item>
      <!-- Tags nav link -->
      <v-list-item>
        <router-link to="/tags" class="router-link">
          <v-list-item-icon class="list-item-icon">
            <v-icon>mdi-tag</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Tags</v-list-item-title>
          </v-list-item-content>
        </router-link>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
import { ref } from "vue";
const drawer = ref(false);
</script>

<style scoped>
.router-link {
  display: flex;
  align-items: center;
  width: 100%;
  text-decoration: none;
  color: inherit;
}

.list-item-icon {
  margin-right: 16px;
}
</style>
