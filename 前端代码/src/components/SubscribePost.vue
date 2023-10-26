<template>
      <div @click="onClick(post)" >
      <div >
        <div >
            <div v-if="post.type=='subscribe'&& (parNode.id==1||parNode.id==2)">  <van-cell title="关注" custom-class="vant-cell-bg" is-link value="关注" @click="onClick(post)" size="large" :border="true" /></div>
           {{ post.content.content }}
        </div>
      </div>
        <div v-for="sub in post.chiPost" :key="sub.id" :sub="sub">
          <div v-if="key != parNode.id" >
            <RecNode :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
          </div>
        </div>
      </div>

 
  </template>
<style>

.post-container {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  background-color: #e7fbf9;
}

.post-info {
  font-weight: bold;
  margin-bottom: 10px;
}

.post-content {
  margin-top: 10px;
  padding: 10px;
}

.title {
  font-weight: bold;
}

.child-posts {
  background-color: #83f5bc;
  margin-top: 10px;
  padding: 10px;
}

.parent-posts {
  margin-top: 10px;
}

.content-wrapper {
  margin-bottom: 20px;
}
</style>


<script>
import { createApp,  } from 'vue';
import { useRouter } from "vue-router"

import store from '../store';
import RecNode from './RecNode.vue';
import Expand from '@/scripts/Expand';
import ShowLevel from '@/scripts/ShowLevel';
import {Icon, Cell, CellGroup } from 'vant';
const app = createApp();

app.use(RecNode);
app.use(Icon);
app.use(Cell);
app.use(CellGroup);
export default {
  store,
  name: 'DefaultPost',
  components: {

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
  data() {
    return {
      isContentCollapsed: true,
      contentLimit: 10,
    };
  },
  computed: {
    isContentOverflow() {
      return this.post.content.content.length > this.contentLimit;
    },
  },
  methods: {
    toggleCollapse() {
      this.isContentCollapsed = !this.isContentCollapsed;
    },
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
