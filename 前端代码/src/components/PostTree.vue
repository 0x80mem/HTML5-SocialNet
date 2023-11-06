<template>
  <!-- <RecNode :post="Post" :parNode="Post" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode> -->
  <RecNode v-if="Post" :post="Post" :parNode="Post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
</template>

<script>

import { createApp, ref,onMounted } from 'vue';
import store from '../store';
import RecNode from './RecNode.vue';
import Recursive from '@/scripts/Recursive';

import * as api from'../store/api'
const app = createApp();
app.use(RecNode)

export default {
store,
name: 'PostTree',
components: {
  RecNode,
},
props: {
  id: {
      type: Number,
      default: 0
  },
  showLevel: {
      type: String,
      default: 'full'
  },
},
setup(props) {
  const getFunc = async (id) => {
    const post = await api.getPost(id);
    return post;
  }
  const deleteFunc = (id) => {
    return id;
  }


  let Post = ref(null); // 使用 ref 来初始化 Post
  let visit = ref(null);

  onMounted(async () => {
    Post.value = await getFunc(props.id); // 等待异步操作完成后赋值给 Post
    visit.value = Recursive(Post.value, props.showLevel, getFunc, deleteFunc);
  });

  return {
    Post,
    visit,
    getFunc,
    deleteFunc,
  };
},
};
</script>