<template>
    <div>
      <van-button
        icon="info-o"
        tips="更多信息"
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
    },
  };
  </script>