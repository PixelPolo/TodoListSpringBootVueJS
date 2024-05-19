<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="pa-0 mt-1">
        <v-card>
          <v-card-title>{{ tag.title }}</v-card-title>
          <v-form ref="form" v-model="valid">
            <v-row>
              <!-- Tag title -->
              <v-col cols="12">
                <v-text-field
                  v-model="newTitle"
                  label="Modify title"
                  :rules="textRules"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
          <!-- Modify buttons -->
          <v-card-actions class="justify-end">
            <v-btn color="primary" :disabled="!valid" @click="modifyTag"
              >Modify</v-btn
            >
            <v-btn color="error" @click="deleteTag">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import service from "@/services/service";
import { ref } from "vue";

// ******************************************
// Initialization
// ******************************************

// Define tag object props for the component
const props = defineProps({
  tag: Object,
});

// Define events
const emits = defineEmits(["tag-deleted", "tag-updated"]);

// Reactive references
const valid = ref(false);
const newTitle = ref("");

// Validation rules for the text field
const textRules = [(v) => !!v || "Title is required"];

// ******************************************
// Methods
// ******************************************

// Delete a Tag
const deleteTag = async () => {
  const success = await service.tags.delete(props.tag.id);
  emits("tag-deleted", props.tag.id);
};

// Modify a Tag
const modifyTag = async () => {
  const updatedTag = { title: newTitle.value };
  const success = await service.tags.update(props.tag.id, updatedTag);
  emits("tag-updated", props.tag.id);
};
</script>

<style scoped></style>
