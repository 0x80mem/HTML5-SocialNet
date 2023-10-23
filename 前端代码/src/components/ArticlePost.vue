<template>
    <div @click="onClick(post)" >
      {{ post.id }} {{ parNode.id }} {{ post.showLevel }}    {{ post.type }} 
      <post-content :content="post.content" :showLevel="post.showLevel" :type="post.type"></post-content>
        <div v-if="post.chiPost.length && typeof(post.chiPost[0]) == 'object'">
            <div v-for="sub in post.chiPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id">
                    <RecNode :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
                </div>
            </div>
        </div>
        <div v-if="post.parPost.length && typeof(post.parPost[0]) == 'object'">
            <div v-for="sub in post.parPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id" class="content-wrapper">
                    <RecNode :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
.post-content-wrapper {
  display: flex;
}

.post-content {
  margin-right: 20px;
}

.content-wrapper {
  margin-bottom: 20px;
}

.post-type {
  margin-right: 10px;
  font-weight: bold;
}
</style>
<script>
import { createApp,  } from 'vue';
import { useRouter } from "vue-router"
import PostContent from './PostContent.vue';
import store from '../store';
import RecNode from './RecNode.vue';
import Expand from '@/scripts/Expand';
import ShowLevel from '@/scripts/ShowLevel';
import {Icon, Cell, CellGroup } from 'vant';
const app = createApp();
app.use(PostContent);
app.use(RecNode);
app.use(Icon);
app.use(Cell);
app.use(CellGroup);
export default {
  store,
  name: 'DefaultPost',
  components: {
    PostContent,
    RecNode,
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
    const router = useRouter()
   
    const expand = (post, visit, showLevel, getFunc, deleteFunc) => {
        Expand(post, visit, showLevel, getFunc, deleteFunc)
    }
    const onClick = (post) => {
        if (post.showLevel == ShowLevel['title'])
            router.push({path: "", query: {id: post.id}})
    }
    return {
       
     
        expand,
        onClick,
    };
  },
};
</script>