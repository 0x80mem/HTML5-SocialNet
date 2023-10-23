<template>
    <div>
      <div class="editor-title-container">
        <input v-model="title" class="editor-title" placeholder="在这里输入标题" />
      </div>
      <Editor v-model="content" name="content" id="content" title="Editor" :api-key="apiKey" :init="editorConfig"></Editor>
    </div>
  </template>

  <script>
  import Editor from "@tinymce/tinymce-vue";

  import { ref, reactive, toRefs } from 'vue';

  export default {
    components: {
      Editor,
    },
    props: {
      editorConfig: Object, // 通过props接收编辑器配置
    },
    setup() {
      const content = ref("");
      const title = ref("");
      const apiKey = "zsbmp4e9znnl7a3rjlcj83ak105fquww17uttqptbt66e39e";

      const tiny = reactive({
        apiKey: apiKey,
      });

      return {
        content,
        ...toRefs(tiny),
        title,
        apiKey,
      };
    },
  };
  </script>

  <style scoped>
  .editor-title-container {
    width: 100%;
    border: 1px solid transparent;
    border-radius: 5px;
  }

  .editor-title {
    width: 100%;
    border: none;
    outline: none;
    padding: 10px;
    background: #f5f5f5; /* 更浅的灰色背景 */
  }
  </style>
