<template>

<div v-touch:panright="onPanupLeft">
    <nav-bar title="Recommend"></nav-bar>
    <div>
        <li v-for="item in postList" :key="item"  
         v-touch:panright="onPanupLeft"
         >
         <post-tree :id="item" showLevel="zip"></post-tree>
        </li>

    </div>
    <tool-basket>
        <home-tool></home-tool>
    </tool-basket>

<van-popup
  v-model:show="showLeft"
  position="left"
  :style="{ width: '50%', height: '100%' }"
>
<UserView />
</van-popup>
</div>
  </template>

<style lang="less">
    li {
        list-style: none;
    }
</style>
  
<script>
import store from '../store';
import { createApp, ref } from 'vue';
import { ActionBar, ActionBarIcon, ActionBarButton, Popup } from 'vant';
import { mapState } from 'vuex';
import UserView from './UserView.vue'
import NavBar from '@/components/AppNav.vue';
import ToolBasket from '@/components/ToolBasket.vue';
import HomeTool from '@/components/HomeTool.vue';
import { Button } from 'vant';
import PostTree from '@/components/PostTree.vue';

const app = createApp();
app.use(ActionBar);
app.use(ActionBarIcon);
app.use(ActionBarButton);
app.use(Popup);

export default{
    store,
    components: {
        UserView,
        NavBar,
        ToolBasket,
        HomeTool,
        PostTree,
        [Button.name]: Button
    },
    computed: {
        ...mapState(['postList'])
    },
    setup() {
        const showLeft = ref(false)
        const onPanupLeft = ()=> {
            showLeft.value = true
        }
        let postList = []
        const getData = () => {
            store.commit("getPostList");
            postList = store.state.postList;
            console.log(postList);
        }
        getData()
    return {
        showLeft,
        postList,
        onPanupLeft,
        getData
    };
  },
}

</script>