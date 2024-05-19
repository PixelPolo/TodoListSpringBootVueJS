import axios from "axios";

axios.defaults.baseURL = "";

export default {
  setBaseUrl(url) {
    axios.defaults.baseURL = url;
  },

  todos: {
    async create(todo) {
      try {
        const res = await axios.post("/todos/", todo);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async list() {
      try {
        const res = await axios.get("/todos/");
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async update(todoId, todo) {
      try {
        const res = await axios.patch(`/todos/${todoId}`, todo);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async delete(todoId) {
      try {
        await axios.delete(`/todos/${todoId}`);
        return true;
      } catch (err) {
        return false;
      }
    },

    async getTags(todoId) {
      try {
        const res = await axios.get(`/todos/${todoId}/tags/`);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async deleteTags(todoId) {
      try {
        const res = await axios.delete(`/todos/${todoId}/tags/`);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async addTag(todoId, tag) {
      try {
        await axios.post(`/todos/${todoId}/tags/`, tag);
        return true;
      } catch (err) {
        return false;
      }
    },

    async removeTag(todoId, tagId) {
      try {
        await axios.delete(`/todos/${todoId}/tags/${tagId}`);
        return true;
      } catch (err) {
        return false;
      }
    },
  },

  tags: {
    async create(tag) {
      try {
        const res = await axios.post("/tags/", tag);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async delete(tagId) {
      try {
        await axios.delete(`/tags/${tagId}`);
        return true;
      } catch (err) {
        return false;
      }
    },

    async update(tagId, tag) {
      try {
        const res = await axios.patch(`/tags/${tagId}`, tag);
        return res.data;
      } catch (err) {
        return false;
      }
    },

    async list() {
      try {
        const res = await axios.get("/tags/");
        return res.data;
      } catch (err) {
        return false;
      }
    },
  },
};
