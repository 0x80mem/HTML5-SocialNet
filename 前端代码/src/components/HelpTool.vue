<template>
  <div>
    <van-button
      @click="clickHelp()"
      icon="question-o"
      :style="{ width: '30px', height: '30px', zIndex: buttonZIndex }"
    ></van-button>
    <div
      v-if="showHelp"
      class="help-mask"
      @click="maskClick"
      @touchmove="onMaskTouchMove"
    ></div>
  </div>
  <van-notify v-model:show="show" type="primary">
    <span>{{ tips }}</span>
  </van-notify>
</template>
  
<style>
.help-mask {
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
import { Button, Icon, showNotify, Notify } from "vant";
import { ref } from "vue";

export default {
  components: {
    [Button.name]: Button,
    [Icon.name]: Icon,
    [Notify.name]: Notify,
  },
  setup() {
    const showHelp = ref(false);
    const buttonZIndex = ref(1);
    const tips = ref("");

    const clickHelp = () => {
      showHelp.value = !showHelp.value;
      if (showHelp.value) {
        tips.value = "点击页面元素查看帮助";
        buttonZIndex.value = 200;
        showNotify({
          type: "primary",
          message: tips,
        });
      } else buttonZIndex.value = 1;
    };

    const maskClick = (event) => {
      if (showHelp.value) {
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
          if (element.hasAttribute("tips")) {
            tips.value = element.getAttribute("tips");
            showNotify({
              type: "primary",
              message: tips,
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
      showHelp,
      buttonZIndex,
      tips,
      clickHelp,
      maskClick,
      onMaskTouchMove,
    };
  },
};
</script>