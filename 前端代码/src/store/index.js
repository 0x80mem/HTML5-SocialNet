import axios from 'axios'
import { createStore } from 'vuex'

export default createStore({
  state: {
    userInfo: {
      id: 0,
      username: ''
    },
    postList: []
  },
  getters: {
  },
  mutations: {
    Login(state, [username, password])
    {
      console.log(username)
      console.log(password)
      axios.post("/login", {username: username, password: password}).then(res => {
        state.userInfo.id = res;
        state.userInfo.username = username;
      }).catch(res=>{
        console.log(res);
        state.userInfo.username = username;
      })
    },
    getPostList(state)
    {
      axios.post('/queryPageOrderByTime', {startNum: 0, pageSize: 99999}).then(res=>{
        state.postList = res.data;
      }).catch(res => {
        console.log(res);
        console.log(state.userInfo.username);
        for (var i = 0; i < 10; i++)
            state.postList.push(state.userInfo.username + " " + i);
      })
    },
    post(state, content)
    {
      axios.post('/post', {id: state.userInfo.id, post: content}).then(res=>{
        console.log(res);
      }).catch(res => {
        console.log(res);
      })
      state.postList.push(state.userInfo.username + " " + content)
    }
  },
  actions: {
  },
  modules: {
  }
})
