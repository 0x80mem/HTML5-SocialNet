<template>
<van-sidebar v-model="active">
    <div v-if="isLogin">
        <van-sidebar-item :title="username" @click="toUserInfo()" />
        <van-sidebar-item title="个人中心" @click="toUserInfoEdit()"/>
        <van-sidebar-item title="退出登录" @click="logout()"/>
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
        router.push("/login")
    }
    const logout = ()=> {
      store.commit("logout")
      window.location.href = "/";
    }
    const toUserInfo = ()=> {
      console.log(" 新号别搞",store.state.userInfo)
      router.push({path: "/", query: {id: store.state.userInfo.id}})
    }
    const toUserInfoEdit = ()=> {
      console.log(" 新号别搞",store.state.userInfo)
      if(store.state.userInfo.isLogin)
        router.push({path: "/infoEdit",query: {id: store.state.userInfo.id}})
    }
    return { 
        active,
        isLogin,
        username,
        login,
        logout,
        toUserInfo,
        toUserInfoEdit 
    };
  },
};
</script>