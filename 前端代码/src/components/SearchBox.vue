<template>
  <van-search
    v-model="searchTerm"
    placeholder="请输入搜索关键词"
    @search="handleSearch"
  />
</template>

<script>
export default {
  data() {
    return {
      searchTerm: '', // 用于存储搜索关键词
    };
  },
  methods: {
    handleSearch() {
      // 处理搜索事件
      this.$emit('search', this.searchTerm);
    },
  },
};
</script>
