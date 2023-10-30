import axios from 'axios'
import { createStore } from 'vuex'
import { dlogin, dgetPostList } from './debug_interface'


var api=axios.create({ 
  baseURL:'http://47.93.10.201/api', 
  timeout:1000,
   })
var apiFm=axios.create({ 
  baseURL:'http://47.93.10.201/api', 
  timeout:1000,
    headers: {
          'Content-Type': 'multipart/form-data'
   }
  })
export default createStore({
  namespaced: true,
  state: {
    userInfo: {
      id: 0,
      isLogin: false,
      username: ''
    },
    postList: [],
    isDebug: true,
  },
  getters: {
  },
  mutations: {
    login(state, [username, password])
    {
      if (state.isDebug)
      {
        state.userInfo.id = dlogin(username, password);
        state.userInfo.isLogin = true;
        state.userInfo.username = username;
        return;
      }
      console.log(username)
      console.log(password)
      apiFm.post("/user/login", {name: username, password: password}).then(res => {
        state.userInfo.id = res;
        state.userInfo.isLogin = true;
        state.userInfo.username = username;
        console.log(res);
      }).catch(res=>{
        console.log(res);
      })
    },
    logout(){
      apiFm.get("/user/logout").then(res => {
        console.log(res);
      }).catch(res=>{
        console.log(res);
      })
    },
    register(state,[username,password]){
      apiFm.post('/user/register',{name:username,password:password}).then(res=>{
        console.log(username);
        console.log(res)
        state.userInfo;
      }).catch(res=>{
        console.error(res);
      })
    },
    accurateQueryByName([name]){
      apiFm.post('/user/accurateQueryByName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    fuzzyQueryByName([name]){
      apiFm.post('/user/fuzzyQueryByName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    queryByID([id]){
      apiFm.post('/user/queryByID', {id:id}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    setName([name]){
      apiFm.patch('/user/setName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    setPassword([password]){
      apiFm.patch('/user/setPassword', {password:password}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    cancel(){
      apiFm.delete('/user/setPassword').then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    getPostList(state)
    {
      if (state.isDebug)
      {
        state.postList = dgetPostList();
        console.log("getPostList", state.postList)
        return;
      }
      apiFm.get('/post/queryPageOrderByTime', {
        params:{
          startNum: 0,
          pageSize: 99999
      }
      }).then(res=>{
        console.log(res);
        state.postList = res.data;
      }).catch(res => {
        console.log(res);
      })
    },
    post(state, content,title)
    {
      apiFm.post('/post/post', {
        content:{
          title,
          content
        }
      }).then(res=>{
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
      state.postList.push(state.userInfo.username + " " + content)
    },
    share([postId,shared,shareNode,userId])
    {
      apiFm.post('/rel/post', {postId: postId,shared:shared,shareNode:shareNode,userId:userId}).then(res=>{
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
    },
    queryByContent(state,[content])
    {
      apiFm.get('/post/queryByContent', {
        params:{
          content: content
        }
      }).then(res=>{
        state.postList=res.data;
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
    },
    queryByAuthor(state,[userId])
    {
      apiFm.get('/post/queryByAuthor', {
        params:{
          userId:userId
        }
      }).then(res=>{
        state.postList=res.data;
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
    },
    queryPageOrderByTime(state,[startNum,pageSize])
    {
      apiFm.get('/post/queryPageOrderByTime',{
        params:{
          startNum:startNum,
          pageSize:pageSize
        }
      }).then(res=>{
        state.postList=res.data.data.data;
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    delete_post([id])
    {
      apiFm.delete('/post/ delete', {
        params:{
          id: id
        }
      }).then(res=>{
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
    },
    collect([postId,collected,collectNode,userId]){
      apiFm.post('/rel/collect',{postId:postId,collected:collected,collectNode:collectNode,userId:userId}).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
  like(state,info){ 
      console.log(info)
   apiFm.post('/rel/like', {
                
                postId:info.postId,
                collected:info.collected,
                collectNode:info.collectNode,
                liked:info.liked,
                likeNode:info.likeNode,
                userId:info.userId
                
        }
).then(res=>{ 
console.log(res); 
}).catch(err=>{ 
console.log(err); 
}) 
},
    cancelCollect([postId,collectNode]){
      api.delete('/rel/cancelCollect',{
        params:{
          postId:postId,
          collectNode:collectNode
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    cancelLike(state,info){ 
    apiFm.delete('/rel/cancelLike',
              {data:{
          postId:info.postId, 
          liked:info.liked, 
        likeNode:info.likeNode 
                    }
              }
    ).then(res=>{ 
      console.log(res); 
     }).catch(err=>{ 
     console.log(err); 
     }) 
     },
    cancelShare([postId,shared,shareNode]){
      apiFm.delete('/rel/cancelShare',{
        params:{
          postId:postId,
          shared:shared,
          shareNode:shareNode
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    createNode([id,type,title,content]){
      apiFm.post('/node/createNode',{
          id:id,  
          type:type,
          content:{
            title,
            content
          }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })

    },
    removeNode([parId,nodeId]){
      apiFm.delete('/node/removeNode',{
        params:{
          parId:parId,
          nodeId:nodeId
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    updateContent([id,content,title]){
      apiFm.post('/node/updateContent',{
        id:id,
        content:{
          title,
          content
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    getContent([id]){
      apiFm.get('/node/getContent',{
        params:{
          id:id
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    }
   

  },

  actions: {
  },
  modules: {
  }
})
