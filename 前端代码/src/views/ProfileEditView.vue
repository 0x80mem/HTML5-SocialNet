<template>
  <app-nav :title="InfoEdit"></app-nav>
  <div class="profile-edit">
    <div class="avatar-section" @click="handleAvatarClick" @mouseover="handleAvatarHover" @mouseleave="handleAvatarLeave">
      <div class="avatar-container">
        <img :src="user.avatar" alt="User Avatar" class="avatar" />
        <input ref="avatarInput" type="file" @change="handleAvatarChange" accept="image/*" class="hidden" />
        <div v-if="isHovered" class="upload-overlay">更改头像</div>
      </div>
    </div>
    <div class="form-section">
      <label for="username">用户名:</label>
      <input type="text" v-model="user.username" id="username" />
      <label for="bio">简介:</label>
      <div class="bio-container">
        <textarea v-model="user.bio" id="bio" :maxlength="maxBioLength" @input="updateBioCount"
          :disabled="bioDisabled"></textarea>
        <div class="bio-count">{{ bioCount }} / {{ maxBioLength }}字</div>
      </div>
    </div>
    <button @click="saveChanges">保存</button>
  </div>
  <tool-basket>
        <home-tool></home-tool>
    </tool-basket>
</template>

<script>
import { changeAvatar,changeInfo,getUserInfo } from '@/store/api';
import { useRoute } from "vue-router";
import ToolBasket from '@/components/ToolBasket.vue';
import HomeTool from '@/components/HomeTool.vue';
export default {
  components: {
        ToolBasket,
        HomeTool,
    },
  data() {
    return {
      user: {
        username: "",
        bio: "",
        avatar: "",
      },
      isHovered: false,
      maxBioLength: 50,
      bioCount: 0,
      bioDisabled: false,
    };
  },
  methods: {
    handleAvatarClick() {
      this.$refs.avatarInput.click();
    },
    async handleAvatarChange(event) {
      const file = event.target.files[0];
      console.log("Selected file:", file);
      let data = new FormData();
      data.append('image',file);
      const result = await changeAvatar(data);
      if(result!=null)
        this.user.avatar=result;
      // 处理头像上传逻辑
      // 可以在这里执行上传逻辑，例如使用 FormData 或其他方式上传头像
    },
    handleAvatarHover() {
      this.isHovered = true;
    },
    handleAvatarLeave() {
      this.isHovered = false;
    },
    updateBioCount() {
      this.bioCount = this.user.bio.length;
    },
    async saveChanges() {
      // 处理保存修改逻辑
      // 可以将修改后的用户信息提交到服务器或进行其他处理
      console.log("User changes saved:", this.user);
      await changeInfo(this.user);
      this.$forceUpdate();
    },
    async fetchUserData(id) {
      const userInfo = await getUserInfo(id);
      if (userInfo != null)
        this.user = userInfo 
    }
  },
  async created() {
    const route = useRoute();
    // 在组件创建时发起 Axios 请求
    await this.fetchUserData(route.query.id);
  },
};
</script>

<style scoped>
/* ... 其他样式 ... */
.profile-edit {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  border-radius: 8px;
}

.avatar-section {
  text-align: center;
  margin-bottom: 20px;
}

.avatar-container {
  position: relative;
  display: inline-block;
  border-radius: 50%;
  overflow: hidden;
}

.hidden {
  display: none;
}

.avatar {
  width: 120px;
  height: 120px;
  object-fit: cover;
  cursor: pointer;
  /* 添加悬停时的指针样式 */
}

.upload-overlay {
  position: absolute;
  width: 120px;
  height: 120px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.7);
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s;
  display: flex;
  /* 使用 flex 布局 */
  justify-content: center;
  /* 在主轴上居中 */
  align-items: center;
  /* 在交叉轴上居中 */
}

.upload-overlay:hover {
  opacity: 1;
}

.form-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  width: 100%;
  /* 使label宽度充满父级容器 */
}

input,
textarea {
  width: 100%;
  /* 使input和textarea宽度充满父级容器 */
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: none;
}

button {
  background-color: #28a745;
  color: #fff;
  padding: 12px 20px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
}

button:hover {
  background-color: #218838;
}

.bio-container {
  position: relative;
  display: flex;
  /* 使用 flex 布局 */
  flex-direction: column;
  /* 设置为纵向布局 */
  align-items: center;
  /* 元素在交叉轴上居中 */
  width: 100%;
}

textarea[disabled] {
  background-color: #f8f9fa;
  resize: none;
  width: 100%;
  box-sizing: border-box;
}

.bio-count {
  position: absolute;
  bottom: 20px;
  right: 0px;
  color: #6c757d;
}</style>
