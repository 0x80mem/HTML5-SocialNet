import axios from 'axios'
import { createStore } from 'vuex'

export default createStore({
  state: {
    isDebug: true,
    userInfo: {
      id: 0,
      isLogin: false,
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
      axios.post("/api/user/login", {name: username, password: password}).then(res => {
        state.userInfo.id = res;
        state.userInfo.username = username;
        state.userInfo.isLogin = true;
      }).catch(res=>{
        console.log(res);
        if (state.isDebug)
        {
          state.userInfo.username = username;
          state.userInfo.isLogin = true;
        }
      })
    },
    getPostList(state)
    {
      axios.get('api/post/queryPageOrderByTime', {params:{startNum: 0, pageSize: 99999}}).then(res=>{
        state.postList = res.data.records;
        console.log(res);
      }).catch(res => {
        console.log(res);
        if (state.isDebug)
        {
          console.log(state.userInfo.username);
          for (var i = 0; i < 10; i++)
              state.postList.push(state.userInfo.username + " " + i);
        }
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
