<template>
    <div>
        <li v-for="item in postList" :key="item">
            <p>{{ item }}</p> 
        </li>

    </div>
<van-action-bar>
  <van-action-bar-button icon="plus" @click="onClickButton" />
</van-action-bar>

<van-popup
  v-model:show="showBottom"
  position="bottom"
  :style="{ height: '50%' }"
>
<EditorView />
</van-popup>

  </template>

<style lang="less">
    li {
        list-style: none;
    }
</style>
  
<script>
import store from '@/store';
import { createApp, ref } from 'vue';
import { ActionBar, ActionBarIcon, ActionBarButton, Popup } from 'vant';
import EditorView from './EditorView.vue';

const app = createApp();
app.use(ActionBar);
app.use(ActionBarIcon);
app.use(ActionBarButton);
app.use(Popup);

export default{
    store,
    components: {
        EditorView
    },
    data() {

        store.commit("getPostList");
        let postList = store.state.postList
        return {
            postList
        }
    }, 
    setup() {
        const showBottom = ref(false)
        const onClickButton = ()=> {
            showBottom.value = true
        }
    return {
        showBottom,
        onClickButton,
    };
  },
}

</script>