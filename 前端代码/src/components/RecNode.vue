<template>
    <div v-if="typeof(post) == 'object'">
        <div v-if="post.type == 'post'">
          <article-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></article-post>
        </div>
        <div v-else-if="post.type == 'user'">
          <user-info-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></user-info-post>
        </div>
        <div v-else>
          <default-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></default-post>
        </div>
    </div>
</template>

<script>
import { createApp,  } from 'vue';
import store from '../store';
import DefaultPost from './DefaultPost.vue';
import ArticlePost from './ArticlePost.vue';
import UserInfoPost from './UserInfoPost.vue';

const app = createApp();
app.use(DefaultPost);
  
export default {
  store,
  name: 'RecNode',
  components: {
    DefaultPost,
    ArticlePost,
    UserInfoPost,
  },
  props: {
    post: {
        type: Object,
    },
    parNode: {
        type: Object,
    },
    visit: {
        type: Set,
    },
    getFunc: {
        type: Function,
    },
    deleteFunc: {
        type: Function,
    }
  },
  setup() {
    return {
    }; 
  },
};
</script>