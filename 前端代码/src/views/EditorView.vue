<!-- eslint-disable no-unused-vars -->
<template>
  <van-form @submit="onSubmit">
      <Editor v-model="content" name="content" id="content"  title="Editor" api-key="apiKey" :init="init" />
    <div style="margin: 16px;" class="tinymce-boxz">

      <van-button round block type="primary" native-type="submit">
        提交
     </van-button>
    </div>
  </van-form>
</template>

<script>
import { createApp} from 'vue';
import { Form, Field, CellGroup } from 'vant';
import store from '@/store';
import Editor from "@tinymce/tinymce-vue";
import { reactive, toRefs,ref } from "@vue/reactivity";

const app = createApp();
app.use(Form);
app.use(Field);
app.use(CellGroup);

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "About",
  components: {
    Editor,
  },
  setup() {
    const content = ref('');

    // 定义 TinyMCE 初始化配置对象 init
    // eslint-disable-next-line no-unused-vars
    const tiny = reactive({
    apiKey: "zsbmp4e9znnl7a3rjlcj83ak105fquww17uttqptbt66e39e", 
    init: {
      language: "zh_CN", //语言类型
      placeholder: "在这里输入文字", //textarea中的提示信息
      min_width: 320,
      min_height: 220,
      height: 500, //注：引入autoresize插件时，此属性失效
      resize: "both", //编辑器宽高是否可变，false-否,true-高可变，'both'-宽高均可，注意引号
      branding: false, //tiny技术支持信息是否显示
      // statusbar: false,  //最下方的元素路径和字数统计那一栏是否显示
      // elementpath: false, //元素路径是否显示
      toolbar_location:'bottom',
      font_formats:
        "微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;", //字体样式
      plugins:
      'code advlist lists image imagetools emoticons customButton',
      toolbar: [
      'image emoticons forecolor backcolor bold italic underline strikethrough link anchor | customButton'
      ], //工具栏配置，设为false则隐藏



      paste_data_images: true, //图片是否可粘贴
      //此处为图片上传处理函数
      // eslint-disable-next-line no-unused-vars
      images_upload_handler: (blobInfo, success, failure) => {
        // 这里用base64的图片形式上传图片,
        let reader = new FileReader(); //本地预览
        reader.readAsDataURL(blobInfo.blob());
        reader.onloadend = function () {
          const imgbase64 = reader.result;
          success(imgbase64);
        };
      },

      file_picker_types: "file image media", //file image media分别对应三个类型文件的上传：link插件，image和axupimgs插件，media插件。想屏蔽某个插件的上传就去掉对应的参数
      // 文件上传处理函数
      file_picker_callback: function (callback, value, meta) {
        // 使用案例http://tinymce.ax-z.cn/general/upload-images.php
        // meta.filetype  //根据这个判断点击的是什么file image media

        let filetype; //限制文件的上传类型,需要什么就添加什么的后缀
        if (meta.filetype == "image") {
          filetype = ".jpg, .jpeg, .png, .gif, .ico, .svg";
        } else if (meta.filetype == "media") {
          filetype = ".mp3, .mp4, .avi, .mov";
        } else {
          filetype =
            ".pdf, .txt, .zip, .rar, .7z, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .mp3, .mp4, .jpg, .jpeg, .png, .gif, .ico, .svg";
        }
        let inputElem = document.createElement("input"); //创建文件选择
        inputElem.setAttribute("type", "file");
        inputElem.setAttribute("accept", filetype);
        inputElem.click();
        inputElem.onchange = () => {
          let file = inputElem.files[0]; //获取文件信息

          // 所有都转成base64文件流,来自官方文档https://www.tiny.cloud/docs/configure/file-image-upload/#file_picker_callback
          let reader = new FileReader();
          reader.readAsDataURL(file);
          reader.onload = function () {
            // Note: Now we need to register the blob in TinyMCEs image blob
            // registry. In the next release this part hopefully won't be
            // necessary, as we are looking to handle it internally.
            let id = "blobid" + new Date().getTime();
            // eslint-disable-next-line no-undef
            let blobCache = tinymce.activeEditor.editorUpload.blobCache;
            let base64 = reader.result.split(",")[1];
            let blobInfo = blobCache.create(id, file, base64);
            blobCache.add(blobInfo);

            // call the callback and populate the Title field with the file name
            callback(blobInfo.blobUri(), { title: file.name });
          };
        };
      },
    },
  });

      const onSubmit = () => {
      store.commit('post', content.value);
      };

  // 在 onMounted 钩子中初始化 TinyMCE


    return {
      content,
      ...toRefs(tiny),
      onSubmit
    };
  },
};
</script>
<style scoped>
.tinymce-boxz > textarea {
display: none;
}
</style>
<style>
/* 隐藏apikey没有绑定当前域名的提示 */
.tox-notifications-container .tox-notification--warning {
display: none !important;
}

.tox.tox-tinymce {
max-width: 100%;
}
</style>