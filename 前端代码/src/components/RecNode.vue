<template>
  <div v-if="typeof(post) == 'object'">
      <div v-if="post.type == 'post'">
        <article-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></article-post>
      </div>
      <div v-else-if="post.type == 'user'">
        <user-info-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></user-info-post>
      </div>
      <div v-else-if="post.type == 'subscribe'">
        <subscribe-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></subscribe-post>
      </div>
      <div v-else-if="post.type == 'fans'">
        <Fans-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></Fans-post>
      </div>
      <div v-else-if="post.type == 'collect'">
        <Collect-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></Collect-post>
      </div>
      <div v-else-if="post.type == 'like1111'">
        <default-post :post="post" :parNode="parNode" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></default-post>
      </div>
      <div v-else>
        
      </div>
  </div>
</template>

<script>
/* eslint-disable */ 
import { createApp,  } from 'vue';
import store from '../store';
import DefaultPost from './DefaultPost.vue';
import ArticlePost from './ArticlePost.vue';
import UserInfoPost from './UserInfoPost.vue';
import SubscribePost  from './SubscribePost.vue';
import FansPost  from './FansPost.vue';
import CollectPost  from './CollectPost.vue';
const app = createApp();
app.use(DefaultPost);
app.use(SubscribePost);
app.use(FansPost);
app.use(CollectPost);
export default {
store,
name: 'RecNode',
components: {
  DefaultPost,
  ArticlePost,
  UserInfoPost,
  SubscribePost,
  FansPost,
  CollectPost,
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
  console.log('rec',props.post)
  return {

  }; 
},
};
</script>
