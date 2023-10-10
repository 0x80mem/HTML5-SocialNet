import axios from 'axios'
import { createStore } from 'vuex'
import { dlogin } from './debug_interface'

var api = axios.create({
  baseURL:'http://8.134.166.163/api',
  timeout:1000
})
export default createStore({
  state: {
    userInfo: {
      id: 0,
      isLogin: false,
      username: ''
    },
    postList: [],
    isDebug: false,
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
      api.post("/user/login", {name: username, password: password}).then(res => {
        state.userInfo.id = res;
        state.userInfo.isLogin = true;
        state.userInfo.username = username;
        console.log(res);
      }).catch(res=>{
        console.log(res);
      })
    },
    logout(){
      api.get("/user/logout").then(res => {
  
        console.log(res);
      }).catch(res=>{
        console.log(res);
      })
    },
    register(state,[username,password]){
      api.post('/user/register',{name:username,password:password}).then(res=>{
        console.log(username);
        console.log(res)
        state.userInfo;
      }).catch(res=>{
        console.error(res);
      })
    },
    accurateQueryByName([name]){
      api.post('/user/accurateQueryByName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    fuzzyQueryByName([name]){
      api.post('/user/fuzzyQueryByName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    queryByID([id]){
      api.post('/user/queryByID', {id:id}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    setName([name]){
      api.patch('/user/setName', {name:name}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    setPassword([password]){
      api.patch('/user/setPassword', {password:password}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    cancel(){
      api.delete('/user/setPassword').then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
    },
    getPostList(state)
    {
      api.get('/post/queryPageOrderByTime', {
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
      api.post('/post/post', {
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
      api.post('/rel/post', {postId: postId,shared:shared,shareNode:shareNode,userId:userId}).then(res=>{
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
    },
    queryByContent(state,[content])
    {
      api.get('/post/queryByContent', {
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
      api.get('/post/queryByAuthor', {
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
      api.get('/post/queryPageOrderByTime',{
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
      api.delete('/post/ delete', {
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
      api.post('/rel/collect',{postId:postId,collected:collected,collectNode:collectNode,userId:userId}).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    like([postId,collected,collectNode,liked,likeNode,userId]){
      api.post('/rel/like',
      {postId:postId,collected:collected,collectNode:collectNode,liked:liked,likeNode:likeNode,userId:userId}).then(res=>{
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
    cancelLike([postId,liked,likeNode]){
      api.delete('/rel/cancelLike',{
        params:{
          postId:postId,
          liked:liked,
          likeNode
        }
      }).then(res=>{
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    cancelShare([postId,shared,shareNode]){
      api.delete('/rel/cancelShare',{
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
      api.post('/node/createNode',{
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
      api.delete('/node/removeNode',{
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
      api.post('/node/updateContent',{
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
      api.get('/node/getContent',{
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
