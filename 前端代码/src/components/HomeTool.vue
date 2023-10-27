<template>
  <div>
    <van-button
      @click="goHome"
      icon="home-o"
      tips="主页"
      style="width: 30px; height: 30px"
    ></van-button>
  </div>
</template>
  
<script>
import { Button } from "vant";

export default {
  components: {
    [Button.name]: Button,
  },
  methods: {
    goHome() {
      window.location.href = "/";
    },
  },
};
</script>
