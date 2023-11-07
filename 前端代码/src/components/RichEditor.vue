<template>
  <div>
    <div class="editor-title-container">
      <input v-model="title" class="editor-title" placeholder="在这里输入标题" />
    </div>
    <Editor v-model="content" name="content" id="content" title="Editor" :api-key="apiKey" :init="editorConfig" @change="contentChanged"></Editor>
    <button @click="submitForm" class="button">提交</button>
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

console.log('Editor Content:', content.value);

// 定义一个数组来存储提取的文本内容
var textArray = [];
var formData = new FormData();
var filteredContent = content.value.replace(/(<img[^>]*>)/g, function() {
  var replaceString = "<img>" ;

  return replaceString;
});
filteredContent = filteredContent.replace(/<p>/g,"");
filteredContent = filteredContent.replace(/<\/p>/g,"");

var topJ=0;
var endJ=0;
if(filteredContent[1]=='i'&&filteredContent[2]=='m'&&filteredContent[3]=='g'){
  topJ=1;
}
if(filteredContent[filteredContent.length-2]=='g'&&filteredContent[filteredContent.length-3]=='m'&&filteredContent[filteredContent.length-4]=='i'){
  endJ=1;
}
console.log(topJ);
console.log(endJ);
textArray=filteredContent.split(/(<img>)/g);
textArray = textArray.filter(function(substring) {
return substring !== "";
});


for(var r=0;r<textArray.length;r++){
  if(textArray[r]=="<img>"){
      textArray[r]=" ";
  }
}
console.log('Text Array:', textArray);
console.log('Editor Content（replace）:', filteredContent);
formData.append("title",title.value);
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
console.log("ac is :", formData.get('articleContent'));
store.commit("post",formData);
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

.button {
display: inline-block;
padding: 10px 20px;
background-color: #3498db;
color: #ffffff;
border: none;
border-radius: 20px;
cursor: pointer;
transition: background-color 0.3s;
width: 100%;
}

.button:hover {
background-color: #2980b9;
}

.button:active {
background-color: #1c638d;
}

</style>
