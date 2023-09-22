<template>
<van-sidebar v-model="active">
    <div v-if="isLogin">
        <van-sidebar-item :title="username" />
        <van-sidebar-item title="标签名称1" />
    </div>
    <div v-else>
        <van-sidebar-item title="未登录" />
        <van-sidebar-item title="登录" @click="login()"/>
    </div>
</van-sidebar>
</template>
  
<script>
  // @ is an alias to /src
import { createApp, ref } from 'vue';
import { useRouter } from "vue-router"
import { Sidebar, SidebarItem } from 'vant';
import store from '../store';

const app = createApp();
app.use(Sidebar);
app.use(SidebarItem);
export default {
  store,
  setup() {
    const router = useRouter()
    const active = ref(0);
    const isLogin = store.state.userInfo.isLogin
    const username = store.state.userInfo.username
    const login = ()=> {
        console.log('login')
        router.push("/login")
    }
    return { 
        active,
        isLogin,
        username,
        login
    };
  },
};
</script>