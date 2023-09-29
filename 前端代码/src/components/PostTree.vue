<template>
    <RecNode :post="post" :parNode="post" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
</template>

<script>

import { createApp,  } from 'vue';
import store from '../store';
import RecNode from './RecNode.vue';

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
  },
  setup() {
    const getFunc = (id) => {
        return new Post(id)
    }
    const deleteFunc = (id) => {
        return new Post(id)
    }
    const Post = getFunc(this.props.id)
    return {
        Post, 
        getFunc,
        deleteFunc,
    }; 
  },
};
</script>