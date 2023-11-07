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
      <div v-else-if="post.type == 'like'">
      
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
import SubscribePost  from './SubscribePost.vue';
import FansPost  from './FansPost.vue';

const app = createApp();
app.use(DefaultPost);
app.use(SubscribePost);
app.use(FansPost);

export default {
store,
name: 'RecNode',
components: {
  DefaultPost,
  ArticlePost,
  UserInfoPost,
  SubscribePost,
  FansPost,
 
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
  console.log('RecNode props is :',props.post.type)
  return {

  }; 
},
};
</script>
