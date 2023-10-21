<template>
  <div class="container">
    <van-button type="primary" icon="arrow-up" @click="showContent = !showContent"></van-button>
      <div class="content" v-if="showContent">
          <slot v-if="showContent"></slot>
      </div>
  </div>
</template>

<style>
.container {
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  position: fixed;
  bottom: 20px;
  right: 20px;
}

.content {
  display: flex;
  flex-direction: row;
  align-items: center;
  max-width: none;
  gap: 10px;
  margin-right: 10px;
}
</style>

<script>
import { onMounted, ref } from 'vue';
import { Icon, Button } from 'vant';

export default {
  components: {
    [Icon.name]: Icon,
    [Button.name]: Button
  },
  setup(props, { attrs, slots }) {
    const showContent = ref(false);

    onMounted(() => {
      const components = slots.default().filter(slot => slot.componentOptions);
      components.forEach((component) => {
        Object.keys(attrs).forEach(attr => {
          if (attr.startsWith('on')) {
            component.el.addEventListener(attr.slice(2), attrs[attr]);
          } else {
            component.componentOptions.propsData[attr] = attrs[attr];
          }
        });
      });
    });

    return {
      showContent
    }
  }
}
</script>