<template>
    <div @click="onClick(post)" >
        <div v-if="post.parPost.length && typeof(post.parPost[0]) == 'object'">
            <div v-for="sub in post.parPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id" class="content-wrapper">
                    <RecNode :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
                </div>
            </div>
        </div>

 <!-- {{ post.id }} {{ parNode.id }} {{ post.showLevel }}    {{ post.type }} | -->
  <div v-if="post.type=='user'"><van-icon name="friends-o" /> 
  
     <post-content :content="post.content" :showLevel="post.showLevel" :type="post.type"></post-content>
    </div>
    
    <van-cell-group inset>
        
    <div v-if="post.type=='subscribe' && (parNode.id==1||parNode.id==2)">  <van-cell title="关注" is-link value="关注" @click="onClick(post)" size="large" :border="true" /></div>
  
    <div v-if="post.type=='fans'&&(parNode.id==1||parNode.id==2)">  <van-cell title="粉丝" is-link value="粉丝" @click="onClick(post)" size="large" :border="true" /></div>
  
    <div v-if="post.type=='post'&&(parNode.id==1||parNode.id==2)">  <van-cell title="文章" is-link value="文章" @click="onClick(post)" size="large" :border="true" /> </div>
        <div  v-if="parNode.id==7"><post-content :content="post.content" :showLevel="post.showLevel" :type="post.type"></post-content></div>
   
</van-cell-group>
        <div v-if="post.chiPost.length && typeof(post.chiPost[0]) == 'object'">
            <div v-for="sub in post.chiPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id">
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
import Post from '@/scripts/Post';
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
