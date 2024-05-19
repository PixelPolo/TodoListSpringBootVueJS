<template>
  <!-- Create todo toggle card -->
  <v-container>
    <v-row>
      <v-col cols="12" class="pa-0 mt-1">
        <v-card>
          <v-card-title @click="toggleCard">Create new todo</v-card-title>
          <v-card-text v-show="isOpen">
            <v-form ref="form" v-model="valid">
              <v-row>
                <!-- Todo title  -->
                <v-col cols="12" sm="6">
                  <v-text-field
                    v-model="newTodo.title"
                    label="Title"
                    :rules="textRules"
                    required
                  ></v-text-field>
                </v-col>
                <!-- Todo order -->
                <v-col cols="6" sm="2">
                  <v-text-field
                    v-model.number="newTodo.order"
                    label="Order"
                    type="number"
                    :rules="numberRules"
                  ></v-text-field>
                </v-col>
                <!-- Todo completed -->
                <v-col cols="6" sm="4">
                  <v-checkbox
                    v-model="newTodo.completed"
                    label="Completed"
                  ></v-checkbox>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <!-- Save button -->
          <v-card-actions class="justify-end" v-show="isOpen">
            <v-btn
              small
              variant="outlined"
              width="100"
              :disabled="!valid"
              @click="saveNewTodo"
            >
              Save
            </v-btn>
          </v-card-actions>
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

// Define a custom event
const emit = defineEmits(["todo-created"]);

// Reactive references
const valid = ref(false);
const isOpen = ref(false);

// New todo as a reactive object reference
const newTodo = ref({
  title: null,
  order: 1,
  completed: false,
});

// Validation rules for the text field
const textRules = [(v) => !!v || "Title is required"];

// Validation rules for the number field
const numberRules = [
  (v) => {
    v === null ||
      v === undefined ||
      v === "" ||
      (Number.isInteger(v) && v > 0) ||
      "Order must be a positive integer";
  },
];

// ******************************************
// Methods
// ******************************************

// Function to toggle the visibility of the card
const toggleCard = () => {
  isOpen.value = !isOpen.value;
};

// Function to save the new todo
const saveNewTodo = async () => {
  const res = await service.todos.create(newTodo.value);
  if (res !== false) {
    newTodo.value.title = null;
    newTodo.value.order = 1;
    newTodo.value.completed = false;
    isOpen.value = false; // Close the card
    emit("todo-created"); // Emit the event
  }
};
</script>

<style scoped></style>
