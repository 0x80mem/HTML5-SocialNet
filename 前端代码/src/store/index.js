import axios from 'axios'
import { createStore } from 'vuex'
var api=axios.create({
  baseURL:'/api',
  timeout:1000
})
export default createStore({
  state: {
    //存储用户信息
    userInfo: {
      id: 0,
      username: ''
    },
    //存储帖子信息 数组形式
    postList: []
  },
  getters: {
  },
  mutations: {
    Login(state, [username, password])
    {
      console.log(username)
      console.log(password)
      api.post("/user/login", {name: username, password: password}).then(res => {
        state.userInfo.id = res;
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
      api.post('/api/user/register',{name:username,password:password}).then(res=>{
        console.log(username);//我们可以试一下
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
      api.post('/post/queryPageOrderByTime', {startNum: 0, pageSize: 99999}).then(res=>{
        state.postList = res.data.records;
      }).catch(res => {
        console.log(res);
        console.log(state.userInfo.username);
        for (var i = 0; i < 10; i++)
        state.postList.push(state.userInfo.username + " " + i);
      })
    },
    post(state, content)
    {
      api.post('/post/post', {id: state.userInfo.id, post: content}).then(res=>{
        console.log(res);
      }).catch(err => {
        console.log(err);
      })
      state.postList.push(state.userInfo.username + " " + content)
    },
    share([postId])
    {
      api.post('/post/post', {postId: postId}).then(res=>{
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
    queryByAuthor(state,[author])
    {
      api.get('/post/queryByAuthor', {
        params:{
          author: author
        }
      }).then(res=>{
        state.postList=res.data;
        console.log(res);
      }).catch(err => {
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

  },

  actions: {
  },
  modules: {
  }
})
