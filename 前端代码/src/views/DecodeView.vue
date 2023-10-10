<template>
    <div>
      <div v-if="type == 'recommend'">
        <recommend-view></recommend-view>
      </div>
      <div v-else-if="type == 'id'">
        <van-popup
          v-model:show="showBottom"
          position="bottom"
          :style="{ height: '50%' }"
        >
          <EditorView />
        </van-popup>
        <post-tree :id="id" showLevel="full"></post-tree>
        <button @click="showBottom = true" class="edit-button">写文章</button>
      </div>
      <div v-else>
        <button @click="showBottom = false" class="edit-button">写文章</button>
      </div>
    </div>
  </template>

  <script>
  import { useRoute } from "vue-router";
  import RecommendView from './RecommendView.vue';
  import PostTree from "@/components/PostTree.vue";
  import EditorView from './EditorView.vue';
  import {ref} from "vue";
  export default {
    components: {
      RecommendView,
      PostTree,
      EditorView,
    },
    setup() {
      const route = useRoute();
      const showBottom = ref(false);
      let type = "recommend";
      let id = 1;

      if (route.query.id !== undefined) {
        type = "id";
        id = route.query.id;
      }


      let isLogin = false;

      return {
        type,
        id,
        isLogin,
        showBottom,
      };
    },
  };
  </script>

  <style>
  .edit-button {
    position: absolute;
    top: 10px;
    right: 10px;
  }
  </style>
