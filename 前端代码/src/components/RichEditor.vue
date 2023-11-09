<template>
    <div>
      <div class="editor-title-container">
        <input v-model="title" class="editor-title" placeholder="在这里输入标题" />
      </div>
      <Editor v-model="content" name="content" id="content" title="Editor" :api-key="apiKey" :init="editorConfig" @change="contentChanged"></Editor>
      <button @click="submitForm">提交</button>
    </div>
  </template>

  <script>
  import Editor from "@tinymce/tinymce-vue";
  import store from "../store";
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

      function contentChanged(newContent) {
      // 在内容改变时触发的函数
      console.log('Content Changed:', newContent);

    }
    function submitForm() {
  var count = 0;

  console.log('Editor Content:', content.value);

  // 定义一个数组来存储提取的文本内容
  var textArray = [];
  var formData = new FormData();
  const filteredContent = content.value.replace(/(<img[^>]*>)/g, function() {
    var replaceString = "<img" + count + ">";
    count++;
    return replaceString;
  });
  textArray=filteredContent.split(/<img.+>/);
  for (var s = 0; s < textArray.length; s++) {
    textArray[s] = textArray[s].replace(/<p>/g, "");
    textArray[s] = textArray[s].replace(/<\/p>/g, "");
    textArray[s] = textArray[s].replace(/\n/g,"");
    console.log('Text Array:', textArray[s]);
}
  console.log('Editor Content（replace）:', filteredContent);

  formData.append("articleContent",textArray);



  var editor = document.getElementById("content");
  var inputElements = editor.querySelectorAll("input");
  if (editor) {
    console.log("Input Elements:", inputElements);
    for (var i = 0; i < inputElements.length; i++) {
      console.log(inputElements[i].value);
    }
  }
  let images=[];
  for(var u=0;u<inputElements.length;u++){
    images.push(inputElements[u].files[0]);
  }

  images.forEach((image)=>{
    formData.append('images',image);
  });

  console.log("提交之前");
  store.commit("postsec",formData);
  console.log("提交之后");
}





      return {
        content,
        ...toRefs(tiny),
        title,
        apiKey,
        submitForm,
        contentChanged,

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
