<template>
    <div v-if="typeof(post) == 'object'">
        <default-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></default-post>
    </div>
</template>

<script>
import { createApp,  } from 'vue';
import store from '../store';
import Post from '@/scripts/Post';
import DefaultPost from './DefaultPost.vue';

const app = createApp();
app.use(DefaultPost);
  
export default {
  store,
  name: 'RecNode',
  components: {
    DefaultPost,
  },
  props: {
    post: {
        type: Post,
    },
    parNode: {
        type: Post,
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