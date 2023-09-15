<template>
    <van-form @submit="onSubmit">
        <textarea v-model="textdata" name="content" id="content" cols="30" rows="10"></textarea>
        <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
        提交
        </van-button>
  </div>
    </van-form>
</template>

<script>
import { createApp, ref } from 'vue';
import { Form, Field, CellGroup } from 'vant';
import store from '@/store';

const app = createApp();
app.use(Form);
app.use(Field);
app.use(CellGroup);

export default {
  store,
  setup() {
    const textdata = ref('')
    const onSubmit = () => {
        store.commit("post", textdata.value)
    };

    return {
      textdata,  
      onSubmit
    };
  },
};
</script>
