<template>
  
    
  <div class="post-container" :id="'art_' + post.id" :action-id="JSON.stringify({id:post.id, type:'article', name:title})">
    <div class="post-content">

      <div
        class="title"
        tips="标题"
        @click="onClick(post)"
        v-if="showLevel >= ShowLevel['title']"
      >
        {{ title }}
      </div>
      <div
        class="author"
        tips="作者"
        v-if="showLevel >= ShowLevel['author']"
      >
        <div v-for="author in authors" :key="author.id">
          <div class="authors">
            <div @click="onClick(author.id)"
                 :action-id="JSON.stringify({id:author.id, type:'author', name:author.title})"> 
               {{ author.title }} 
            </div>
            <van-icon name="plus" tips="关注" @click="subscribe(author.id)" />
          </div>
        </div>
      </div>

  

      <div class="content" v-if="showLevel >= ShowLevel['zip']">
        <div v-if="showLevel == ShowLevel['zip']">
          <div class="show-less" tips="缩略内容" @click="showMore()">
            {{ content }}
          </div>
          <div class="options">
            <div class="like" tips="点赞"> <van-icon name="like"  :color="col" @click="clickLike()"/><span>{{ like1 }}</span></div>
            <div class="comment" tips="评论"> <van-icon name="comment" /><span>{{ comment }}</span></div>
            <div class="collect" tips="收藏"> <van-icon name="star" /><span>{{ star }}</span> </div>
            <div class="expand" tips="展开"> <van-icon name="arrow-down" @click="showMore()"/> </div>
          </div>
        </div>
        <div v-else-if="showLevel >= ShowLevel['full']">
          <div class="show-more" tips="内容">
            {{ content }}
          </div>
          <div class="options">
            <div class="like" tips="点赞"> <van-icon name="like" :color="col" @click="clickLike()"/>
              {{like1}}</div>
            <div class="comment" tips="点赞"> <van-icon name="comment" /><span>{{ comment }}</span></div>
            <div class="collect" tips="点赞"> <van-icon name="star" @click="clickStar()"/><span>{{ star }}</span> </div> 
            <div class="expand" tips="点赞"> <van-icon name="arrow-up" @click="showLess()" /> </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
.post-container {
  border: 1px solid #ccc;
  background-color: #fff;
  padding: 10px;
  margin: 10px;
}

.post-content {
  background-color: #fff;
  color: #333;
}

.title,
.author,
.content {
  text-align: left;
}

.title {
  color: #000;
  font-size: large;
  font-weight: bold;
}

.author {
  font-size: small;
  color: #666;
}

.content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: none;
}

.content div {
  width: 100%;
}

.show-less {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  min-height: 3em;
}

.show-more {
  min-height: 3em;
}

.authors {
  display: flex;
}

.options {
  display: flex;
  float: left;
  margin-top: 10px;
}

.options div {
  width: 4em;
}

.options div span{
  margin-left: 5px;
}

.options > .expand {
  flex-grow: 1;
  text-align: right;
}
</style>


<script>
import { createApp, ref } from "vue";
import { useRouter } from "vue-router";
import store from "../store";
import RecNode from "./RecNode.vue";
import Expand from "@/scripts/Expand";
import ShowLevel from "@/scripts/ShowLevel";
import { Icon, Cell, CellGroup } from "vant";
const app = createApp();

app.use(RecNode);
app.use(Icon);
app.use(Cell);
app.use(CellGroup);
export default {
  store,
  name: "DefaultPost",
  components: {},
  props: {
    post: {
      type: Object,
    },
    parNode: {
      type: Object,
    },
    visit: {
      type: Set,
    },
    getFunc: {
      type: Function,
    },
    deleteFunc: {
      type: Function,
    },
  },
  setup(props) {
    const router = useRouter();
// 未点击时默认蓝色
   let col= ref("#1989fa")
    const expand = (post, visit, showLevel, getFunc, deleteFunc) => {
      post = Expand(post, visit, showLevel, getFunc, deleteFunc);
    };
    const onClick = (post) => {
      if (typeof post === "number")
        router.push({ path: "", query: { id: post } });
      else if (post.showLevel >= ShowLevel["title"])
        router.push({ path: "", query: { id: post.id } });
    };
    const subscribe = (id) => {
      console.log("subscribe", id);
    };

    const title = ref("");
    const content = ref("");
    const authors = ref("");
    const showLevel = ref(0);
   const like1=ref(0);
    const star = ref(0);
    const comment = ref(0);
    const clikelike1 = ref(0);
    clikelike1.value = 0;
    const getData = (post) => {
      title.value = post.content.title;
      authors.value = []
      for (let i = 0; i < post.author.length; i++) {
        authors.value.push(Object({
          id: post.author[i],
          title: props.getFunc(post.author[i]).content.title
        }));
      }
      content.value = post.content.content;
      showLevel.value = post.showLevel;
      star.value = 0;
      comment.value = 0;
    };
    getData(props.post);
    const getlike=(post)=> {
      for (let i = 0; i < post.chiPost.length; i++) {
       console.log(props.getFunc(post.chiPost[i]).type);
          if( props.getFunc(post.chiPost[i]).type=='like'){
            console.log("我已成功");
                   like1.value=props.getFunc(post.chiPost[i]).content.title
          }

        }
      };
      getlike(props.post);


    const showMore = () => {
      const prevScrollY = window.scrollY;
      expand(
        props.post,
        props.visit,
        ShowLevel["full"],
        props.getFunc,
        props.deleteFunc
      );
      getData(props.post);
      requestAnimationFrame(function() {
        window.scrollTo({ top: prevScrollY, behavior: 'instant' });
      });
    };

    const showLess = () => {
      expand(
        props.post,
        props.visit,
        ShowLevel["zip"],
        props.getFunc,
        props.deleteFunc
      );
      getData(props.post);
    };
    let postId = "1816142823"; // 从组件的 props 或 data 中获取需要的数据
    let collected = "216498386"; // 例如，假设帖子已经被收藏
    let liked = "585358255"; // 例如，用户已经喜欢过帖子
    let collectNode = "410142786"; // 你的 collectNode 值
    let likeNode = "1277472323"; // 你的 likeNode 值
    let userId = "348228825"; // 用户的 ID
    const clickLike = () => {
      console.log("点击事件生效了")
  if(clikelike1.value==0){console.log(String(postId));
  store.commit("like",{postId:postId,collected:collected,liked:liked,collectNode:collectNode,likeNode:likeNode,userId:userId})
 
   col.value="red";
   clikelike1.value++;
   like1.value++;
   return;
  }
  if(clikelike1.value==1){console.log(String(postId));
   store.commit("cancelLike",{postId:1816142823,liked:216498386,likeNode:913137261})
   col.value="blue";
   clikelike1.value--;
   like1.value--;
   return;
  }
    };
    const clickStar = () => {
      console.log("star", 0);
    };

    return {
      col,
      title,
      authors,
      content,
      showLevel,
      ShowLevel,
      like1,
      star,
      comment,
      expand,
      onClick,
      subscribe,
      showMore,
      showLess,
      clickLike,
      clickStar,
    };
  },
};
</script>
