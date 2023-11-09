<template>
    <van-nav-bar :title="title" @click-left="showUser = true" @click-right="showEditor = true">
      <template v-slot:left>
        <van-icon name="user-o" @click="showUser = true" />
      </template>
      <template v-slot:right>
        <van-icon name="edit" @click="showEditor = true" />
      </template>
    </van-nav-bar>

    <van-popup
      :style="{ width: '50%', height: '100%' }"
      v-model:show="showUser"
      position="left">
      <UserView/>
    </van-popup>
    <van-popup
      :style="{ width: '100%', height: '100%' }"
      closeable
  @click-overlay="onClickOverlay"
  @click-close-icon="onClickCloseIcon"
      v-model:show="showEditor"
      position="buttom">
      <RichEditor :editorConfig="customEditorConfig" v-if="showEditor"  />
    </van-popup>
  </template>


  <style>
  .van-nav-bar {
    position: fixed;
    top: 0;
    width: 100%;
  }
  </style>

  <script>
  import { ref } from 'vue';
  import UserView from '../views/UserView.vue';
  import RichEditor from '@/components/RichEditor.vue';
  import init from '@/scripts/EditorConfig.js';
  import {showToast } from "vant";
  export default {
    components: {
      UserView,
      RichEditor
  },
    props: {
      title: {
        type: String,
        default: 'Recommend',
      },
    },
    setup() {
      const onClickOverlay = () => {
      showToast('click-overlay');
    };
    const onClickCloseIcon = () => {
      showToast('click-close-icon');
    };
      const showUser = ref(false);
      const showEditor = ref(false);
      const customEditorConfig = ref(init)
      return {
        onClickOverlay ,
        onClickCloseIcon,
        showUser,
        showEditor,
        customEditorConfig,
      };
    },
  };
  </script>
