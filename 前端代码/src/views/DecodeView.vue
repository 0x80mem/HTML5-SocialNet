<template>
    <div v-if="type == 'recommend'">
        <recommend-view></recommend-view>
    </div>
    <div v-else-if="type == 'id'">
        <post-tree :id="id" showLevel="full"></post-tree>
    </div>
</template>
  
<script>

import { useRoute } from "vue-router"
import RecommendView from './RecommendView.vue';
import PostTree from "@/components/PostTree.vue";

export default{
  components: { 
    RecommendView,
    PostTree,
  },
  setup()
  {
      const route = useRoute()
      let type = "recommend"
      let id = 1
      if (route.query.id != undefined)
          type = "id"
          id = route.query.id
      return {
          type,
            id,
      }
  }
}

</script>