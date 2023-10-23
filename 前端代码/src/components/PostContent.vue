<template>
    {{ content.title }}
    {{ content.content }}
</template>

<script>

import { createApp,  } from 'vue';
import store from '../store';
import { Form } from 'vant';

const app = createApp();
app.use(Form);
  
export default {
  store,
  name: 'PostContent',
  props: {
    content: {
        type: Object,
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