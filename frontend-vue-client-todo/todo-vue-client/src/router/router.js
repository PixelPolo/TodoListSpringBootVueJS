import { createRouter, createWebHistory } from "vue-router";
import Todos from "@/views/Todos.vue";
import Tags from "@/views/Tags.vue";
import Index from "@/views/index.vue";

const routes = [
  {
    path: "/",
    name: "Index",
    component: Index,
  },
  {
    path: "/todos",
    name: "Todos",
    component: Todos,
  },
  {
    path: "/tags",
    name: "Tags",
    component: Tags,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
