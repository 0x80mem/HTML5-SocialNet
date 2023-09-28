<template>
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
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
  <van-field
    v-model="password1"
    name="validator"
    label="请确认密码"
    placeholder="密码确认"
    :rules="[{validator, message: '请输入与之相同的密码' }]"
  />
  
</van-cell-group>
<div style="margin: 16px;">
  <router-link  to="/account">
    <van-button round block type="primary" style="padding-top: 5px;margin-top: 20px;">
    注册
  </van-button>
  </router-link>
</div>
</van-form>
<div class="a1">
  <h2>
    <span>Y</span>
  </h2>
  </div>
  </template>
  <style>
      .a1 {
        position: relative;
        height: 500px;
        width: 100%;
      }
      h2{
        text-align: center;
        color : rgb(62, 89, 98);
        font-size: 400px;
        font-weight: 200;
        position: absolute;
        top: -50%;
        left: 50%;
        transform: translateX(-50%);
        font-family:Arial, Helvetica, sans-serif;
      }
          h2 a {
              text-decoration: none;
              color: white;
          }
          h2 span {
              transition: 0.5s;
          }
          h2:hover span:nth-child(1) {
              margin-right: 10px;
          }
          h2:hover span:nth-child(3) {
              margin-left: 30px;
          }
          h2 span:nth-child(1)::after {
              opacity: 0;
              transition: 2s;
          }/*透明度，过渡持续时间*/
          h2:hover span {
              color: white;
              text-shadow: 0 0 10px #5f2121,
                  0 0 20px #5f2121,
                  0 0 40px #5f2121,
                  0 0 80px #5f2121;
          }/*文本阴影*/  
  </style>
  <script>
// @ is an alias to /src
import { createApp, ref } from 'vue';
import { Form, Field, CellGroup,Toast } from 'vant';
import store from '@/store';
const app = createApp();
app.use(Form);
app.use(Field);
app.use(CellGroup);
app.use(Toast);

export default {
store,
setup() {
  const username = ref('');
  const password = ref('');
  const password1 = ref('');
  
  const onSubmit = (values) => {
    store.commit("Login", [values['username'], values['password'],values['password1']])
  };
  const validator = function () {
    if(password.value !== password1.value)
    return false
  }
  
  return {
    username,
    password,
    password1,
    onSubmit,
    validator
  };
},

};


</script>
