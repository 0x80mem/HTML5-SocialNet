<template>

<div v-touch:panright="onPanupLeft" class="v-touch" >
    <nav-bar title="Recommend"></nav-bar>
    <div>
        <div class="search">
            <SearchBox @search="performSearch" />
        </div>
        <li v-for="item in postList" :key="item"  
         v-touch:panright="onPanupLeft" 
         class="v-touch"
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
<style>

    .v-touch{
        touch-action: pan-y!important;
    }
    .search{
        margin-top: 60px;
    }
    
</style>
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
import SearchBox from '@/components/SearchBox.vue';
import { getPostListByContent } from '@/store/api';
export default{
    methods: {
        async performSearch(searchTerm) {
            // 在这里执行搜索逻辑，使用 search term（搜索关键词）
            console.log('Performing search for:', searchTerm);
            // 可以在这里触发异步请求，更新搜索结果等
            const list = await getPostListByContent(searchTerm)
            if(list!=null){
                this.$store.commit('updatePostList', list);
                console.log('list', this.postList);
            }
                
        },
    },
    store,
    components: {
        UserView,
        NavBar,
        ToolBasket,
        HomeTool,
        PostTree,
        [Button.name]: Button,
        SearchBox
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
