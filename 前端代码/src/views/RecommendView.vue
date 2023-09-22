<template>

<div v-touch:panup="onClickButton"
     v-touch:panright="onPanupLeft"
>
    <div>
        <li v-for="item in postList" :key="item"  
         v-touch:panup="onClickButton"
         v-touch:panright="onPanupLeft"
         >
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
import EditorView from './EditorView.vue';
import UserView from './UserView.vue'

const app = createApp();
app.use(ActionBar);
app.use(ActionBarIcon);
app.use(ActionBarButton);
app.use(Popup);

export default{
    store,
    components: {
        EditorView,
        UserView,
    },
    setup() {
        store.commit("getPostList");
        const showBottom = ref(false)
        const showLeft = ref(false)
        const onClickButton = ()=> {
            if (showLeft.value != true)
                showBottom.value = true
        }
        const onPanupLeft = ()=> {
            if (showBottom.value != true)
                showLeft.value = true
        }
        let postList = store.state.postList
        const getData = ()=> {
            postList = store.state.postList
        }
    return {
        showBottom,
        showLeft,
        postList,
        onClickButton,
        onPanupLeft,
        getData
    };
  },
}

</script>