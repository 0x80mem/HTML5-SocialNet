<template>
  <div>
    <van-button
      @click="clickAction()"
      icon="apps-o"
      tips="更多操作"
      :style="{ width: '30px', height: '30px', zIndex: buttonZIndex }"
    ></van-button>
    <div
      v-if="showAction"
      class="action-mask"
      @click="maskClick"
      @touchmove="onMaskTouchMove"
    ></div>
    <van-action-bar
      v-if="showAction && navTitle != ''"
      :style="{
        position: 'fixed',
        display: 'flex',
        width: '100% !important',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: buttonZIndex,
        background: 'white',
        position: 'fixed',
        width: '100%',
      }"
    >
        <van-action-bar-icon icon="like" tips="点赞" text="点赞"></van-action-bar-icon>
        <van-action-bar-icon icon="comment" tips="评论" text="评论"></van-action-bar-icon>
        <van-action-bar-icon icon="star" tips="收藏" text="收藏"></van-action-bar-icon>
        <van-action-bar-icon icon="share" tips="转发" text="转发"></van-action-bar-icon>
    </van-action-bar>
    <van-notify v-model:show="show" type="primary"
      v-if="showAction && navTitle != ''">
        <span>{{ navTitle }}</span>
    </van-notify>
    </div>
</template>
    
<style>
.action-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1);
  z-index: 100;
}
</style>
    
  <script>
import { Button, Icon, ActionBar, showNotify, closeNotify, Notify } from "vant";
import { ref } from "vue";

export default {
  components: {
    [Button.name]: Button,
    [Icon.name]: Icon,
    [ActionBar.name]: ActionBar,
    [Notify.name]: Notify,
  },
  setup() {
    const showAction = ref(false);
    const buttonZIndex = ref(1);
    const navTitle = ref("");
    const clickAction = () => {
      showAction.value = !showAction.value;
      if (showAction.value) {
        navTitle.value = "点击页面元素进行操作";
        buttonZIndex.value = 200;
        showNotify({
          type: "primary",
          message: navTitle,
          duration: 0,
        });
      } else
      {
        buttonZIndex.value = 1;
        navTitle.value = "";
        closeNotify();
      }
    };

    const maskClick = (event) => {
      if (showAction.value) {
        const x = event.clientX;
        const y = event.clientY;
        const elements = document.elementsFromPoint(x, y); // 获取指定位置下的所有元素
        let targetElement = null;
        for (const element of elements) {
          const zIndex = parseInt(
            getComputedStyle(element).getPropertyValue("z-index")
          );
          if (zIndex != 100) {
            targetElement = element;
            break;
          }
        }
        let element = targetElement;
        while (element !== null) {
          if (element.hasAttribute("action-id")) {
            let attr = JSON.parse(element.getAttribute("action-id"));
            navTitle.value = attr.name;
            showNotify({
              type: "primary",
              message: navTitle,
              duration: 0,
            });
            break;
          }
          element = element.parentElement;
        }
      }
    };

    const onMaskTouchMove = (event) => {
      event.preventDefault();
    };

    return {
      showAction,
      buttonZIndex,
      navTitle,
      clickAction,
      maskClick,
      onMaskTouchMove,
    };
  },
};
</script>
