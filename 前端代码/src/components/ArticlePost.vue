<template>
  <div class="post-container" :id="'art_' + post.id">
    <div class="post-content">
      <div
        class="title"
        @click="onClick(post)"
        v-if="showLevel >= ShowLevel['title']"
      >
        {{ title }}
      </div>
      <div
        class="author"
        v-if="showLevel >= ShowLevel['author']"
      >
        <div v-for="author in authors" :key="author.id">
          <div class="authors">
            <div @click="onClick(author.id)"> {{ author.title }} </div>
            <van-icon name="plus" @click="subscribe(author.id)" />
          </div>
        </div>
      </div>
      <div class="content" v-if="showLevel >= ShowLevel['zip']">
        <div v-if="showLevel == ShowLevel['zip']">
          <div class="show-less" @click="showMore()">
            {{ content }}
          </div>
          <div class="options">
            <div class="like"> <van-icon name="like" /><span>{{ like }}</span></div>
            <div class="comment"> <van-icon name="comment" /><span>{{ comment }}</span></div>
            <div class="collect"> <van-icon name="star" /><span>{{ star }}</span> </div>
            <div class="expand"> <van-icon name="arrow-down" @click="showMore()"/> </div>
          </div>
        </div>
        <div v-else-if="showLevel >= ShowLevel['full']">
          <div class="show-more">
            {{ content }}
          </div>
          <div class="options">
            <div class="like"> <van-icon name="like" @click="clickLike()"/><span>{{ like }}</span></div>
            <div class="comment"> <van-icon name="comment" /><span>{{ comment }}</span></div>
            <div class="collect"> <van-icon name="star" @click="clickStar()"/><span>{{ star }}</span> </div> 
            <div class="expand"> <van-icon name="arrow-up" @click="showLess()" /> </div>
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
    const like = ref(0);
    const star = ref(0);
    const comment = ref(0);
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
      like.value = 0;
      star.value = 0;
      comment.value = 0;
    };
    getData(props.post);

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

    const clickLike = () => {
      console.log("like", 0);
    };
    const clickStar = () => {
      console.log("star", 0);
    };

    return {
      title,
      authors,
      content,
      showLevel,
      ShowLevel,
      like,
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
