<template>

<div>
    <UserProfile :user="currentUser" />
</div>

  <div class="di" style="display: flex;padding: 50px 50px;">
    

    
    <button id="dsadas" @click="login">
      login
    </button>
    <button @click="logout">
      logout
    </button>
    <button id="dsadas" @click="like">
      like
    </button>
    <button id="dsadas" @click="ulike">
      cancel like
    </button>
    <button id="dsadas" @click="accurateQueryByName">
      accurateQueryByName
    </button>
    <button @click="fuzzyQueryByName">
      fuzzyQueryByName
    </button>
    <button @click="queryByID">
      queryByID
    </button>
    <button @click="setName">
      setName
    </button>
    <button @click="setPassword">
      setPassword
    </button>
    <button @click="cancel">
      cancel
    </button>
    <button @click="getPostList">
      getPostList
    </button>

  </div>
  <div class="">
    <button @click="post">
      post
    </button>
    <button @click="queryPostById">
      queryPostById
    </button>
    <button @click="queryByContent">
      queryByContent
    </button>
    <button @click="queryByAuthor">
      queryByAuthor
    </button>
    <button @click="deletePost">
      deletePost
    </button>

  </div>
  <div>
    <button @click="share">
      share
    </button>
    <button @click="cancelShare">
      cancelShare
    </button>
    <button @click="collect">
      collect
    </button>
    <button @click="cancelCollect">
      cancelCollect
    </button>
    
  </div>
  <div>
    <button @click="createNode">
      createNode
    </button>
    <button @click="removeNode">
      removeNode
    </button>
    <button @click="updateContent">
      updateContent
    </button>
    <button @click="getContent">
      getContent
    </button>
    <button @click="like">
      like
    </button>
    <button @click="ulike">
      ulike
    </button>
    <button @click="comment">
      comment
    </button>
    <button @click="getAup">
      getAup
    </button>
    
  </div>
</template>


<script>
import store from '../store';
import test_case from "../store/test_case1";
import UserProfile from'./testView/UserProfile.vue'
import * as api from '../store/api';
import * as deb from '../store/debug_interface'
import Post from '@/scripts/Post';
import Content from '@/scripts/Content';
export default {
  components: {
    UserProfile,
    },
  store,
  data() {
    return {
      currentUser: {
        username: 'John Doe',
        bio: 'Web Developer',
        avatar: 'http://47.93.10.201/img/5.png',
      },
    };
  },
  setup() {
   
    const login = () => {
      store.commit("login", ['zzr', '123456'])
    };
    const like = () => {
      api.like(225997051)
    }
    const ulike = () => {
      api.uLike(225997051)
    }
    //由于session问题暂不可用
    const logout = () => {
      store.commit("logout")
    }
    const accurateQueryByName = () => {
      store.commit("accurateQueryByName", "admin")
    }
    const fuzzyQueryByName = () => {
      store.commit("fuzzyQueryByName", "a")
    }
    const queryByID = () => {
      store.commit("queryByID", 856215219)
    }
    //由于session问题暂不可用
    const setName = () => {
      store.commit("setName", "name")
    }
    //由于session问题暂不可用
    const setPassword = () => {
      store.commit("setPassword", "password")
    }
    //由于session问题暂不可用
    const cancel = () => {
      store.commit("cancel")
    }
    const getPostList = () => {
      store.commit("getPostList")
    }
    //由于session问题暂不可用
    const post = () => {
      document.getElementById("1").ariaValueMax;
      store.commit("post", {
        "node": {
          "content": {
            "title": "登高",
            "content": "渚青沙白鸟飞回"
          }
        }

      })
    }

    const queryByContent = () => {
      store.commit("queryByContent", "天")
    }
    const queryByAuthor = () => {
      store.commit("queryByAuthor", "856215219")
    }
    //由于session问题暂不可用
    const deletePost = () => {
      store.commit("deletePost", "1816142823")
    }
    const share = () => {
      store.commit("share", {
        postId: 1816142823,
        shared: 612013902,
        shareNode: 1243289469,
        userId: 348228825
      })
    }
    const cancelShare = () => {
      store.commit("cancelShare", {
        postId: 1816142823,
        shared: 612013902,
        shareNode: 1243289469,

      })
    }
    const collect = () => {
      store.commit("collect", {
        postId: 1816142823, 
        collected: 585358255,
        collectNode: 410142786, 
        userId: 348228825
      })
    }
    const cancelCollect = () => {
      store.commit("cancelCollect", {
        postId: 1816142823, 
        collectNode: 410142786, 
      })
    }
    const createNode = () => {
      store.commit("createNode", {
        "id":410142786,
        "type": "collect_group",
        "content":{
            "title":"zzr",
            "content":"风急天高猿啸哀"
        }
      })
    }
    //暂不可用
    const removeNode = () => {
      // store.commit("removeNode", {
      //   parId:1080601603,
      //   nodeId:317138151
      // })
      console.log(JSON.parse(JSON.stringify(test_case[1])))
    }
    const updateContent = () => {
      store.commit("updateContent", 
        {
            "id": 1306036182,
            "content": {
                "title": "ShacooKL",
                "content": "updateContent test"
            }
        }
      )
    }
    const getContent = () => {
      store.commit("getContent",1816142823)
    }
    const queryPostById = async() => {
      // const re=  api.getPost(1026073299);
      const re = await deb.dgetPost(1026073299)
      console.log(re)

    }
    const comment= async() => {
      let comment = new Post(0,'comment',833460694,new Content('ShacooKL',false,'这是第二条条评论'),[1026073299],[])
      const re = await api.comment(comment);
      console.log(re)
    }
    const getAup= async() => {
      
      const re = await api.getPostListByAuthor(1075404869)
      console.log(re)
    }
    
    return {
      login,
      like,
      ulike,
      logout,
      accurateQueryByName,
      fuzzyQueryByName,
      queryByID,
      setName,
      setPassword,
      cancel,
      getPostList,
      post,
      share,
      queryByContent,
      queryByAuthor,
      deletePost,
      cancelShare,
      collect,
      cancelCollect,
      createNode,
      removeNode,
      updateContent,
      getContent,
      queryPostById,
      comment,
      getAup
    };
  },
};
// @ is an alias to /src
      

</script>

