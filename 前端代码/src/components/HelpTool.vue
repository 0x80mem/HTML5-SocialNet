<template>
  <div>
    <van-button
      @click="showIcon = !showIcon"
      icon="question-o"
      style="width: 30px; height: 30px; z-index: 200"
    ></van-button>
    <div
      v-if="showIcon"
      class="mask"
      @click="maskClick"
      @touchmove="onMaskTouchMove"
    ></div>
  </div>
<van-notify v-model:show="show" type="primary">
  <span>{{tips}}</span>
</van-notify>
</template>
  
<style>
.mask {
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
import { Button, Icon, showNotify } from "vant";
import { ref } from "vue";

export default {
  components: {
    [Button.name]: Button,
    [Icon.name]: Icon,
  },
  setup() {
    const showIcon = ref(false);
    const showTips = ref(false)
    const tips = ref('')

    const maskClick = (event) => {
      if (showIcon.value) {
        showTips.value = true;
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
            console.log(zIndex);
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
      showIcon,
      tips,
      maskClick,
      onMaskTouchMove,
    };
  },
};
</script>