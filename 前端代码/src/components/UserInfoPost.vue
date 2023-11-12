<template>
    <div @click="onClick(post)" class="height">
        <div v-if="post.parPost.length && typeof(post.parPost[0]) == 'object'&&(post.type=='user'||post.type=='subscribe'||post.type=='fans')">
            <div v-for="sub in post.parPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id" class="content-wrapper">
                    <RecNode v-if="sub" :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
                </div>
            </div>
        </div>
        <div v-if="post.type=='user'">
            <!-- <post-content :content="post.content" :showLevel="post.showLevel" :type="post.type"></post-content> -->
            <UserProfile :id = "post.id"></UserProfile>
        </div>
       
    
      
        <div class="chi" v-if="post.chiPost.length && typeof(post.chiPost[0]) == 'object'&&(post.type=='user'||post.type=='subscribe'||post.type=='fans')">
            <div v-for="sub in post.chiPost" :key="sub.id" :sub="sub">
                <div v-if="key != parNode.id">
                    <RecNode v-if="sub" :post="sub" :parNode="post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
.height{
margin-top:80px;
}
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
import UserProfile from './UserProfile.vue';
const app = createApp();
app.use(PostContent);
app.use(RecNode);
app.use(Icon);
app.use(Cell);
app.use(CellGroup);
app.use(UserProfile)
export default {
  store,
  name: 'DefaultPost',
  components: {
    // PostContent,
    UserProfile,
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
  setup(props) {
    console.log('UserInfoPost props.post',props.post)
    const router = useRouter()
   
    const expand =  (post, visit, showLevel, getFunc, deleteFunc) => {
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


