<template>
  <!-- Create Tags Component -->
  <CreateTag @tag-created="refreshTags" />
  <!-- Tags List -->
  <div v-for="tag in tags" :key="tag.id">
    <Tag :tag="tag" @tag-deleted="refreshTags" @tag-updated="refreshTags" />
  </div>
</template>

<script setup>
import Tag from "@/components/Tag.vue";
import service from "@/services/service";
import { ref, watchEffect } from "vue";

// ******************************************
// Initialization
// ******************************************

// Reactive references
const tags = ref([]);

// Load the list of tags from the service
service.tags.list().then((data) => {
  tags.value = data;
});

// ******************************************
// Handle tag-created event
// ******************************************

// Method to refresh tags
const refreshTags = async () => {
  const data = await service.tags.list();
  tags.value = data;
};
</script>
