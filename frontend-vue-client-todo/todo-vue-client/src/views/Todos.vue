<template>
  <!-- Create Todo Component -->
  <CreateTodo @todo-created="refreshTodos" />
  <!-- Filter todo toggle card -->
  <v-container>
    <v-row>
      <v-col cols="12" class="pa-0">
        <v-card>
          <v-card-title @click="toggleCard">Filters</v-card-title>
          <v-card-text v-show="isFilterOpen">
            <v-row>
              <!-- Tags -->
              <v-col cols="12" sm="6">
                <v-select
                  v-model="selTags"
                  :label="'Tags'"
                  :items="tags"
                  item-text="title"
                  item-value="id"
                  multiple
                  chips
                ></v-select>
              </v-col>
              <!-- Completed button -->
              <v-col cols="6" sm="3">
                <v-switch v-model="showCompleted" label="Completed" />
              </v-col>
              <!-- Uncompleted button -->
              <v-col cols="6" sm="3">
                <v-switch v-model="showUncompleted" label="Uncompleted" />
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
  <!-- Todos List -->
  <div v-for="todo in todos" :key="todo.id">
    <Todo
      :todo="todo"
      @todo-updated="refreshTodos"
      @todo-deleted="refreshTodos"
    />
  </div>
</template>

<script setup>
import service from "@/services/service";
import { ref, watch } from "vue";

// ******************************************
// Initialization
// ******************************************

// Reactive references
const todos = ref([]);
const tags = ref([]);
const selTags = ref([]);
const showCompleted = ref(true);
const showUncompleted = ref(true);
const isFilterOpen = ref(false);

// Load the list of todos from the service
service.todos.list().then((data) => {
  todos.value = data;
});

// Load the list of tags from the service
service.tags.list().then((data) => {
  tags.value = data;
});

// ******************************************
// Methods
// ******************************************

// Function to toggle the visibility of the filter card
const toggleCard = () => {
  isFilterOpen.value = !isFilterOpen.value;
};

// Function to filter and refresh the todos
const refreshTodos = async () => {
  const data = await service.todos.list();
  const filtered = data.filter((todo) => {
    // Check for todo completed
    if (!showCompleted.value && todo.completed) {
      return false;
    }
    // Check for todo uncompleted
    if (!showUncompleted.value && !todo.completed) {
      return false;
    }
    // Check for todo's tag in the selected ones
    if (
      selTags.value.length > 0 &&
      !todo.tags.some((tag) => selTags.value.includes(tag.id))
    ) {
      return false;
    }
    return true;
  });
  filtered.sort((a, b) => a.order - b.order);
  todos.value = filtered;
};

// Watch for changes in filter options and refreshTodos if there are
watch([selTags, showCompleted, showUncompleted], refreshTodos);
</script>

<style scoped></style>
