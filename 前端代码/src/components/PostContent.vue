<template>
    {{ content.title }}
    {{ content.content }}
</template>

<script>

import { createApp,  } from 'vue';
import store from '../store';
import { Form } from 'vant';
import Content from '@/scripts/Content';

const app = createApp();
app.use(Form);
  
export default {
  store,
  name: 'PostContent',
  props: {
    content: {
        type: Content,
    },
    showLevel: {
        type: Number,
        default: 0
    },
    type: {
        type: String,
        default: 'post'
    },
  },
  setup() {
    return {
    }; 
  },
};
</script>