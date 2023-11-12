<template>
  <div class="user-profile">
    <div class="avatar-info">
      <img :src="user.avatar" alt="User Avatar" class="avatar" />
      <div class="info">
        <div class="username" @mouseover="onHover" @mouseleave="onLeave">
          {{ user.username }}
        </div>
        <div class="bio">{{ user.bio }}</div>
      </div>
    </div>
    <button v-if="!isSelf" @click.stop="toggleFollow" :class="{ 'follow-btn': true, 'followed': isFollowed }">
      {{ followButtonText }}
    </button>
  </div>
  <div class="separator"></div>
</template>

<script>
import * as api from '../store/api'
import store from '../store'
export default {
  props: {
    id: Number
  },
  methods: {
    onHover() {
      this.isHovered = true;
    },
    onLeave() {
      this.isHovered = false;
    },
    async toggleFollow() {
      let result = false;
      // 根据关注状态调用相应的异步函数
      if (this.isFollowed) {
        // 已关注时，调用取消关注异步函数
        result = await api.uSub(this.$props.id);

      } else {
        // 未关注时，调用关注异步函数
        result = await api.sub(this.$props.id);
      }
      console.log(result)
        if (result != null && result) {
          this.isFollowed = !this.isFollowed;
        }
    },
    async checkFollowStatus() {
      // 检查关注状态
      const subStatus = await api.isSubscribed(this.$props.id);
      this.isFollowed = subStatus; // 将获取的关注状态赋值给 isFollowed
     
    },
    async fetchUserData(id) {
      const userInfo = await api.getUserInfo(id);
      if (userInfo != null)
        this.user = userInfo 
    }
  },
  computed: {
    followButtonText() {
      return this.isFollowed ? '已关注' : '关注 +';
    }
  },
  data() {
    return {
      user: {
        username: '',
        bio: '',
        avatar: ''
      },
      isHovered: false,
      isFollowed: false ,// 初始未关注
      isSelf:false
    };
  },
  async created() {
    // 在组件创建时发起 Axios 请求
    console.log('this.$props.id', this.$props.id);
    await this.fetchUserData(this.$props.id);
    await this.checkFollowStatus();
    if(store.state.userInfo.id==this.$props.id){
        this.isSelf=true;
    }
  }
};
</script>

<style scoped>
.user-profile {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: calc(100% - 20px);
  margin-left: 20px;
}

.avatar-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 20px;
  cursor: pointer;
}

.info {
  display: flex;
  flex-direction: column;
}

.username {
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 8px;
  align-self: flex-start;
}

.username:hover {
  color: blue;
}

.bio {
  color: #555;
  margin: 0;
  align-self: flex-start;
}

button {
  cursor: pointer;
  padding: 8px 16px;
  border: none;
  color: #fff;
  border-radius: 4px;
  transition: background-color 0.3s;
  margin-right: 20px;
}

.follow-btn {
  background-color: blue;
}

.followed {
  background-color: gray;
}
.separator {
  margin-top: 10px;
  border-top: 1px solid #ccc;
  width: 90%;
  margin-left: 10%;
  margin-bottom: 20px;
}
</style>



