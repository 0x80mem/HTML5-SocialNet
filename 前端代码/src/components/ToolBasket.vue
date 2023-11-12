<template>
  <div :class="container_state">
    <div class="basket-content" v-if="showContent">
      <slot v-if="showContent"></slot>
    </div>
    <van-button
      class="basket-tools"
      type="primary"
      icon="arrow-up"
      style="z-index: 201;"
      @click="showContent = !showContent;"
    ></van-button>
  </div>
  <div :class="alter_state">
    <van-button
      class="basket-tools"
      type="primary"
      icon="exchange"
      style="z-index: 201;"
      @click="swapElements()"
    ></van-button>
  </div>
</template>

<style>
.container,
.alter {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  bottom: 10px;
}

.container
{
  right: 10px;
}

.alter {
  left: 10px;
}

.basket-content {
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
  max-width: none;
  gap: 10px;
  margin-bottom: 10px;
}

.basket-content,
.basket-tools {
  width: 30px !important;
  height: 30px !important;
}

</style>

<script>
import { onMounted, ref } from "vue";
import { Icon, Button } from "vant";

export default {
  components: {
    [Icon.name]: Icon,
    [Button.name]: Button,
  },
  setup(props, { attrs, slots }) {
    const showContent = ref(false);

    onMounted(() => {
      const components = slots
        .default()
        .filter((slot) => slot.componentOptions);
      components.forEach((component) => {
        Object.keys(attrs).forEach((attr) => {
          if (attr.startsWith("on")) {
            component.el.addEventListener(attr.slice(2), attrs[attr]);
          } else {
            component.componentOptions.propsData[attr] = attrs[attr];
          }
        });
      });
    });

    const container_state = ref('container')
    const alter_state = ref('alter');
    const swapElements = () => {
      let value = container_state.value
      container_state.value = alter_state.value
      alter_state.value = value
    }

    return {
      container_state,
      alter_state,
      showContent,
      swapElements,
    };
  },
};
</script>