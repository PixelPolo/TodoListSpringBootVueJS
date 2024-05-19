<template>
  <!-- Create tag toggle card -->
  <v-container>
    <v-row>
      <v-col cols="12" class="pa-0 mt-1">
        <v-card>
          <v-card-title @click="toggleCard">Create new tag</v-card-title>
          <v-card-text v-show="isOpen">
            <v-form ref="form" v-model="valid">
              <v-row>
                <!-- Tag title  -->
                <v-col cols="12">
                  <v-text-field
                    v-model="newTag.title"
                    label="Title"
                    :rules="textRules"
                    required
                  ></v-text-field>
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
              @click="saveNewTag"
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
const emit = defineEmits(["tag-created"]);

// Reactive references
const valid = ref(false);
const isOpen = ref(false);

// New tag as a reactive object reference
const newTag = ref({
  title: null,
});

// Validation rules for the text field
const textRules = [(v) => !!v || "Title is required"];

// ******************************************
// Methods
// ******************************************

// Function to toggle the visibility of the card
const toggleCard = () => {
  isOpen.value = !isOpen.value;
};

// Function to save the new tag
const saveNewTag = async () => {
  const res = await service.tags.create(newTag.value);
  if (res !== false) {
    newTag.title = null;
    isOpen.value = false; // Close the card
    emit("tag-created"); // Emit the event
  }
};
</script>

<style scoped></style>
