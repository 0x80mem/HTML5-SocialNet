<template>
    <app-nav :title="UserName"></app-nav>
    <template v-if="UserId !== ''">
        <post-tree :id="UserId" showLevel="full"></post-tree>
    </template>
    <tool-basket>
        <home-tool></home-tool>
    </tool-basket>
</template>

<script>
import AppNav from '@/components/AppNav.vue'
import PostTree from '@/components/PostTree.vue'
import { onMounted, ref } from 'vue';
import { getPost } from '@/store/api';
/* eslint-disable */
export default{
    props: {
        id: {
            type: Number,
            default: 0
        },
    },
    components: { 
        AppNav,
        PostTree,
    },
    setup(props) {
        console.log("UserInfoView 2.")
        const UserName = ref(''); // 使用 ref 来保存用户名
        const UserId = ref('');
        onMounted(async () => {
            const Post = await getPost(props.id);
            UserName.value = Post.content.title;
            UserId.value = props.id
        });
        return {
            UserName,
            UserId
        };
    }
};








</script>
