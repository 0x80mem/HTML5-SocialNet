<template>
    <RecNode :post="Post" :parNode="Post" :visit="visit" :getFunc="getFunc" :deleteFunc="deleteFunc"></RecNode>
</template>

<script>

import { createApp,  } from 'vue';
import store from '../store';
import RecNode from './RecNode.vue';
import Recursive from '@/scripts/Recursive';
import { dgetPost } from '@/store/debug_interface';

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
    const getFunc = (id) => {
        return dgetPost(id);
    }
    const deleteFunc = (id) => {
        return id
    }
    let Post = getFunc(props.id)
    let visit = Recursive(Post, props.showLevel, getFunc, deleteFunc)
    return {
        Post, 
        visit,
        getFunc,
        deleteFunc,
    }; 
  },
};
</script>