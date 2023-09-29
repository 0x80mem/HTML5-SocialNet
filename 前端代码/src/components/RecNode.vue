<template>
    <div v-for="sub in post.parPost" :key="sub.id" :sub="sub">
        <div v-if="key != parNode.id">
            <RecNode :post="sub" :parNode="post" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
        </div>
    </div>
    <post-content :content="post.content" :showLevel="post.showLevel" :type="post.type"></post-content>
    <div v-for="sub in post.chiPost" :key="sub.id" :sub="sub">
        <div v-if="key != parNode.id">
            <RecNode :post="sub" :parNode="post" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
        </div>
    </div>
</template>

<script>
import { createApp,  } from 'vue';
import PostContent from './PostContent.vue';
import store from '../store';
import Post from '@/scripts/Post';

const app = createApp();
app.use(PostContent);
  
export default {
  store,
  name: 'RecNode',
  components: {
    PostContent,
  },
  props: {
    post: {
        type: Post,
        default: (post) => post
    },
    parNode: {
        type: Post,
        default: (post) => post
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