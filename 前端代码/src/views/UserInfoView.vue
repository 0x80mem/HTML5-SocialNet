<template>
    <app-nav :title="UserName"></app-nav>
    <post-tree :id="id" showLevel="full"></post-tree>
    <tool-basket>
        <home-tool></home-tool>
    </tool-basket>
</template>

<script>
import AppNav from '@/components/AppNav.vue'
import PostTree from '@/components/PostTree.vue'
import { dgetPost } from '@/store/debug_interface';

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
        const getFunc = (id) => {
            return dgetPost(id);
        }
        let Post = getFunc(props.id)
        const UserName = Post.content.title
        console.log(Post)
        return {
            UserName,
        }
    },
}
</script>
