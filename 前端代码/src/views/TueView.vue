<template>
  <div>
    <h1>多图片和多段文字上传</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label for="image">选择图片：</label>
        <input type="file" id="image" accept="image/*" multiple @change="handleImageChange">
      </div>
      <div id="image-upload">
        <div v-for="(image, index) in images" :key="index">{{ image.name }} ({{ image.size }} bytes)</div>
      </div>
      <div>
        <label for="text">输入文字：</label>
        <input type="text" id="text" v-model="textInput">
        <button type="button" @click="addText">添加文字</button>
      </div>
      <div id="text-list">
        <div v-for="(text, index) in texts" :key="index">{{ text }}</div>
      </div>
      <button type="submit">上传</button>
    </form>
  </div>
</template>

<script>
import store from '../store';
export default {
  data() {
    return {
      images: [],
      texts: [],
      textInput: '',
      articleContent: '', // 新增一个属性 articleContent
    };
  },
  methods: {
    handleImageChange(e) {
      this.images = Array.from(e.target.files);
    },
    addText() {
      const text = this.textInput;
      if (text=='') {
        this.texts.push("");
      }else{
        this.texts.push(text);
      }
      this.textInput = '';
    },
    submitForm() {
      const formData = new FormData();
      this.images.forEach((image) => {
        formData.append('images', image);
      });
      // 修改 articleContent 的值
      formData.append('articleContent', this.texts.join(', '));

      // 执行 axios 请求或其他操作
      store.commit('post', formData);

      // 重置表单数据
      this.images = [];
      this.texts = [];
      this.textInput = '';
      this.articleContent = '';
    },
  },
};
</script>
