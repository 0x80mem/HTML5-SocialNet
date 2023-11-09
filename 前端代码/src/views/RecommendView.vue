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
    <tool-basket>
        <home-tool></home-tool>
    </tool-basket>
</template>
  
<script>
import store from '../store';
import { ref,watch} from 'vue';
import { mapState } from 'vuex';
import UserView from './UserView.vue'
import NavBar from '@/components/AppNav.vue';
import ToolBasket from '@/components/ToolBasket.vue';
import HomeTool from '@/components/HomeTool.vue';
import { Button } from 'vant';
import PostTree from '@/components/PostTree.vue';

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
        const postList = ref([]); // 使用ref()包装postList

        const getData = () => {
            store.commit("getPostList");
        }

        // 使用watch来监视store.state.postList的变化
        watch(
        () => store.state.postList,
        (newPostList) => {
            // 在postList发生变化时更新本地的postList
            postList.value = newPostList;
        }
        );
        getData(); 
    return {
        showLeft,
        postList,
        onPanupLeft,
        getData
    };
  },
}


</script>
