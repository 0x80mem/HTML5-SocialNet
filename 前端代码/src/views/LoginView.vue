<template>
    <van-form @submit="onSubmit">
  <van-cell-group inset>
    <van-field
      v-model="username"
      name="username"
      label="用户名"
      placeholder="用户名"
      :rules="[{ required: true, message: '请填写用户名' }]"
    />
    <van-field
      v-model="password"
      type="password"
      name="password"
      label="密码"
      placeholder="密码"
      validate
      :rules="[{ required: true,
        pattern: /^.{6,20}$/, 
        message:'密码要在6位~20位之间'}]"
    />
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      登录
    </van-button>
  </div>
</van-form>
  </template>
  
<script>
  // @ is an alias to /src
import { createApp, ref } from 'vue';
import { Form, Field, CellGroup } from 'vant';
import { useRouter } from "vue-router"
import store from '../store';

const app = createApp();
app.use(Form);
app.use(Field);
app.use(CellGroup);
  
export default {
  store,
  setup() {
    const username = ref('');
    const password = ref('');
    const router = useRouter()
    const onSubmit = (values) => {
      store.commit("login", [values['username'], values['password']])
      router.back()
    };

    return {
      username,
      password,
      onSubmit,
    };
  },
};
</script>