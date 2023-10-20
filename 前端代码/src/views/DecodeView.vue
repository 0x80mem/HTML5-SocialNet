<template>
    <div>
      <div v-if="type == 'recommend'">
        <recommend-view></recommend-view>
      </div>
      <div v-else-if="type == 'id'">
        <user-info-view :id="id" />
      </div>
      <div v-else>
      </div>
    </div>
  </template>

  <script>
  import { useRoute } from "vue-router";
  import RecommendView from './RecommendView.vue';
  import {ref} from "vue";
  import UserInfoView from './UserInfoView.vue';
  export default {
    components: {
      RecommendView,
      UserInfoView,
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