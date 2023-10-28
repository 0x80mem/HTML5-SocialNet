<template>
  <div>
    <van-button
      @click="clickInfo()"
      icon="info-o"
      :style="{ width: '30px', height: '30px', zIndex: buttonZIndex }"
    ></van-button>
    <div
      v-if="showInfo"
      class="info-mask"
      @click="maskClick"
      @touchmove="onMaskTouchMove"
    >
      <div class="info-content" @touchstart="onMaskTouchStart" v-if="hasInfo">
        <table>
          <tr>
            <td>ID:</td>
            <td>{{ id }}</td>
          </tr>
          <tr>
            <td>标题:</td>
            <td>{{ title }}</td>
          </tr>
          <tr>
            <td>历史版本:</td>
            <td></td>
          </tr>
          <tr>
            <td>显示原因:</td>
            <td></td>
          </tr>
          <tr>
            <td>查看权限:</td>
            <td></td>
          </tr>
          <tr>
            <td>编辑权限:</td>
            <td></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>
    
  <style>
.info-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.info-content {
  position: fixed;
  text-align: left;
  color: white;
  width: 100%;
  min-height: 100%;
  z-index: 101;
  top: 45px;
  padding-bottom: 50px;
  background: rgba(0, 0, 0, 0.5);
}
.info-content table {
  padding: 10px;
}
.info-content table td {
  vertical-align: top;
}
.info-content table td:first-child {
  white-space: nowrap;
  min-width: 100px;
}
</style>
    
  <script>
import { Button, Icon } from "vant";
import { ref } from "vue";

export default {
  components: {
    [Button.name]: Button,
    [Icon.name]: Icon,
  },
  setup() {
    const showInfo = ref(false);
    const hasInfo = ref(false);
    const buttonZIndex = ref(1);

    const clickInfo = () => {
      if (hasInfo.value) hasInfo.value = false;
      else {
        showInfo.value = !showInfo.value;
        if (showInfo.value) buttonZIndex.value = 200;
        else buttonZIndex.value = 1;
      }
    };

    const id = ref(0);
    const title = ref("");

    const maskClick = (event) => {
      if (showInfo.value) {
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
          if (element.hasAttribute("more-info")) {
            hasInfo.value = true;
            const moreInfo = JSON.parse(element.getAttribute("more-info"));
            id.value = moreInfo.id;
            title.value = moreInfo.name;
            break;
          }
          element = element.parentElement;
        }
      }
    };

    let lastY = 0;
    let originY = 0;
    const onMaskTouchStart = (event) => {
      const infoContent = document.querySelector(".info-content");
      originY = infoContent.offsetTop;
      lastY = event.touches[0].clientY;
    };

    const onMaskTouchMove = (event) => {
      event.preventDefault();
      if (hasInfo.value) {
        const infoContent = document.querySelector(".info-content");
        let newY = originY + event.touches[0].clientY - lastY;
        newY = Math.max(newY, window.innerHeight - infoContent.offsetHeight);
        newY = Math.min(newY, 45);
        infoContent.style.top = newY + "px";
      }
    };

    return {
      showInfo,
      hasInfo,
      buttonZIndex,
      id,
      title,
      clickInfo,
      maskClick,
      onMaskTouchStart,
      onMaskTouchMove,
    };
  },
};
</script>
