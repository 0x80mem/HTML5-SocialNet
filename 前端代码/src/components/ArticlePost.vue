<template>
  <div
    class="post-container"
    :id="'art_' + post.id"
    :action-id="JSON.stringify({ id: post.id, type: 'article', name: title })"
    :more-info="JSON.stringify({ id: post.id, type: 'article', name: title })"
  >
    <div class="post-content">
      <div
        class="title"
        tips="标题"
        @click="onClick(post)"
        v-if="showLevel >= ShowLevel['title']"
      >
        {{ title }}
      </div>
      <div class="author" tips="作者" v-if="showLevel >= ShowLevel['author']">
        <div v-for="author in authors" :key="author.id">
          <div class="authors">
            <div
              @click="onClick(author.id)"
              :action-id="
                JSON.stringify({
                  id: author.id,
                  type: 'author',
                  name: author.title,
                })
              "
            >
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
            <div class="like" tips="点赞">
              <van-icon name="like" :color="col" @click="clickLike(post)" /><span>{{
                like1
              }}</span>
            </div>


            <div class="comment" tips="评论">
              <van-icon name="comment" @click="clickcomment(post)" /><span>{{
                comment1
              }}</span>

              <van-popup
  v-model:show="show"
  position="bottom"
  :style="{ height: '90%' ,width:'100%' }"
  closeable
  @click-overlay="onClickOverlay"
  @click-close-icon="onClickCloseIcon"> 

   <div class="content" v-for="(author, index) in commentauthor" :key="index">
      <p>作者: {{ author }}</p>
      <p>内容: {{ commentcontent[index] }}</p>
  </div>

</van-popup>

            </div>
            

            <div class="collect" tips="收藏">
              <van-icon name="star" /><span>{{ star }}</span>
            </div>
            <div class="expand" tips="展开">
              <van-icon name="arrow-down" @click="showMore()" />
            </div>
          </div>
        </div>
        <div v-else-if="showLevel >= ShowLevel['full']">
          <div class="show-more" tips="内容">
            {{ content }}
          </div>
          <div class="options">
            <div class="like" tips="点赞">
              <van-icon name="like" :color="col" @click="clickLike(post)" />
              {{ like1 }}
            </div>
            <div class="comment" tips="评论">
              <van-icon name="comment" /><span>{{ comment }}</span>
              
            </div>
            <div class="collect" tips="展开">
              <van-icon name="star" @click="clickStar()" /><span>{{
                star
              }}</span>
            </div>
            <div class="expand" tips="内容">
              <van-icon name="arrow-up" @click="showLess()" />
            </div>
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



.options div span {
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
import { Icon, Cell, CellGroup,Popup,showToast } from "vant";
import * as api from '../store/api'
import {  SUCESS } from "@/store/statusCodes";
const app = createApp();

app.use(RecNode);
app.use(Icon);
app.use(Cell);
app.use(CellGroup);
app.use(Popup);
app.use(showToast);
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
      type:  Function ,
    },
    deleteFunc: {
      type: Function,
    },
  },
  setup(props) {
    const show = ref(false);
    const onClickOverlay = () => {
      showToast('click-overlay');
    };
    const onClickCloseIcon = () => {
      showToast('click-close-icon');
    };
    const router = useRouter();
    // 未点击时默认蓝色
    let col = ref("#1989fa");
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
    const like1 = ref(0);//点赞数量
    const star = ref(0);
    const comment = ref(0);
    const comment1 = ref(0);//评论数量
    const clikelike1 = ref(0);
    const clikecomment1 = ref(0);
    const commentauthor=[];
    const commentcontent=[];
    clikecomment1.value=0;
    clikelike1.value = 0;
    
    const getData = async (post) => {
      title.value = post.content.title;
      authors.value = [];

      // 使用 Promise.all 等待所有异步操作完成
      await Promise.all(
        post.author.map(async (authorId) => {
          const authorContent = await props.getFunc(authorId);
          authors.value.push({ id: authorId, title: authorContent.content.title });
        })
      );

      content.value = post.content.content;
      showLevel.value = post.showLevel;
      star.value = 0;
      comment.value = 0;
    };
    getData(props.post);

    const getlike = async (post) => {
      for (let i = 0; i < post.chiPost.length; i++) {
        
        const postContent = await props.getFunc(post.chiPost[i]);
        if (postContent.type === "liked") {
         
          if(store.state.userInfo.isLogin){
            const getLikeStatu = await api.isLikedPost(post.id);
            if(getLikeStatu){
              col.value = 'red';
              clikelike1.value = 1;
            }
              
          }
          like1.value = postContent.content.title;
        }
      }
    };
    getlike(props.post);

    const getcommentnumber = async (post) => {
      comment1.value = 0;
      for (let i = 0; i < post.chiPost.length; i++) {
        const postContent = await props.getFunc(post.chiPost[i]);
        
        if (postContent.type === "comment") {
          
          comment1.value++;
        }
      }
    };
    getcommentnumber(props.post);

    const showMore = async() => {
      const prevScrollY = window.scrollY;
      expand(props.post, props.visit, ShowLevel["full"], props.getFunc, props.deleteFunc);
      await getData(props.post);
      requestAnimationFrame(function () {
        window.scrollTo({ top: prevScrollY, behavior: "instant" });
      });
    };

    const showLess = () => {
      expand(props.post, props.visit, ShowLevel["zip"], props.getFunc, props.deleteFunc);
      getData(props.post);
    };
    const clickcomment = async (post) => {
      show.value = true;
      
      if (clikecomment1.value === 0) {
        for (let i = 0; i < post.chiPost.length; i++) {
          const postContent = await props.getFunc(post.chiPost[i]);
          
          if (postContent.type === "comment") {
           
            commentauthor.push(postContent.content.title);
            commentcontent.push(postContent.content.content);
            
          }
        }
        if (clikecomment1.value === 0) clikecomment1.value++;
      }
    };
    const clickLike =  async(post) => {
      let postId = post.id; // 从组件的 props 或 data 中获取需要的数据
     
      if (clikelike1.value == 0) {
        const newLikePost = await api.like(postId);
        if(newLikePost!=null&&typeof newLikePost !='string'){
            clikelike1.value++;
            col.value = "red";
            like1.value = newLikePost.content.title;
        }
        return;
      }
      if (clikelike1.value == 1) {
        const newLikePost = await api.uLike(postId);
        console.log(newLikePost);
        col.value = "blue";
        clikelike1.value--;
        like1.value= newLikePost.content.title;
        return;
      }
    };
    const clickStar = () => {
      console.log("star", 0);
    };

    return {
      show,
      onClickOverlay,
      onClickCloseIcon,
      col,
      title,
      authors,
      content,
      showLevel,
      ShowLevel,
      like1,
      star,
      comment1,
      comment,
      clikecomment1,
      commentauthor,
      commentcontent,
      expand,
      onClick,
      subscribe,
      showMore,
      showLess,
      clickLike,
      clickcomment,
      clickStar,
    };
  },
};
</script>
