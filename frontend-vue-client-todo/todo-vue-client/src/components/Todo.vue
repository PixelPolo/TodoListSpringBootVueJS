<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="pa-0 mt-1">
        <v-card>
          <v-card-title>{{ todo.title }}</v-card-title>
          <v-row class="ml-2 mr-2">
            <!-- Todo tags -->
            <v-col cols="8">
              <v-select
                v-model="selectedTags"
                :label="'Tags'"
                :items="tags"
                item-text="title"
                item-value="id"
                multiple
                chips
                @update:modelValue="validateForm"
              ></v-select>
            </v-col>
            <!-- Todo order -->
            <v-col cols="4">
              <v-text-field
                v-model.number="todo.order"
                label="Order"
                type="number"
                :rules="numberRules"
              ></v-text-field>
            </v-col>
          </v-row>
          <!-- Buttons -->
          <v-card-actions class="justify-end">
            <v-badge v-if="todo.completed" color="green" inline> </v-badge>
            <v-badge v-else color="red" inline> </v-badge>
            <v-btn color="primary" @click="completeTodo">Complete</v-btn>
            <v-btn color="primary" :disabled="!formValid" @click="modifyTodo"
              >Modify</v-btn
            >
            <v-btn color="error" @click="deleteTodo">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import service from "@/services/service";
import { ref, watch } from "vue";

// ******************************************
// Initialization
// ******************************************

// Define todo object props for the component
const props = defineProps({
  todo: Object,
});

// Define events
const emits = defineEmits(["todo-updated", "todo-deleted"]);

// State for tags
const tags = ref([]);
const selectedTags = ref([]);
let formValid = ref(false);

// Load the list of all tags from the service
service.tags.list().then((data) => {
  tags.value = data;
});

// Load the tags associated with the todo
service.todos.getTags(props.todo.id).then((data) => {
  selectedTags.value = data;
});

// ******************************************
// Methods
// ******************************************

// Method to complete the todo
const completeTodo = async () => {
  props.todo.completed = !props.todo.completed;
  const updatedTodo = {
    title: props.todo.title,
    completed: props.todo.completed,
    order: props.todo.order,
  };
  await service.todos.update(props.todo.id, updatedTodo);
  emits("todo-updated", props.todo.id);
};

// Method to validate the form to change tags
const validateForm = () => {
  formValid.value = true;
};

// Method to modify the todo tags
const modifyTodo = async () => {
  // Delete all todo's tags
  const res = await service.todos.deleteTags(props.todo.id);
  // Add all selected tags to todo's
  const newTags = selectedTags.value;
  for (let i = 0; i < newTags.length; i++) {
    await service.todos.addTag(props.todo.id, {
      id: newTags[i],
    });
  }
  emits("todo-updated", props.todo.id);
};

// Method to change the order
const changeOrder = async (newOrder) => {
  const updatedTodo = {
    title: props.todo.title,
    completed: props.todo.completed,
    order: newOrder,
  };
  await service.todos.update(props.todo.id, updatedTodo);
  emits("todo-updated", props.todo.id);
};

// Watcher the order and call changeOrder if it changes
watch(
  () => props.todo.order,
  (newValue) => {
    changeOrder(newValue);
  }
);

// Method to delete the todo
const deleteTodo = async () => {
  await service.todos.delete(props.todo.id);
  emits("todo-deleted", props.todo.id);
};
</script>

<style scoped></style>
