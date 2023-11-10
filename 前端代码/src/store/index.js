import axios from 'axios'
import { createStore } from 'vuex'
import * as apiFunc from './api'
import { SUCESS } from './statusCodes'
// const baseURL ="http://localhost:8088"
const baseURL ="http://47.93.10.201/api"
const timeout = 10000
const axiosConfig = {
  baseURL,
  timeout,
};

const api = axios.create(axiosConfig);
const apiFm = axios.create({
  ...axiosConfig,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
  },
});
export default createStore({
	state: {
		userInfo: {
			id: 0,
			isLogin: false,
			username: ''
		},
		postList: [],
	},
	getters: {
	},
	mutations: {
		//登录
		login(state, [username, password]) {
			
			api.post("/user/login", { name: username, password: password }).then(res => {
				if(res.data.code==1){
					state.userInfo.id = res.data.data.id;
					state.userInfo.isLogin = true;
					state.userInfo.username = username;
					console.log(res)
				}
				
				
			}).catch(res => {
				console.log(res);
			})
		},
		//登出
		logout(state) {
			api.get("/user/logout").then(res => {
				console.log(res)
				state.userInfo.isLogin=false;
				state.userInfo.id=0;
				state.userInfo.username='';
			}).catch(res => {
				console.log(res);
			})
		},
		setUserInfo(state, userInfo) {
      state.userInfo.id = userInfo.userId;
      state.userInfo.isLogin = true;
      state.userInfo.username = userInfo.userName;
    },
		//注册
		register(state, [username, password]) {
			api.post('/user/register', { name: username, password: password }).then(res => {
				
				state.userInfo;
			}).catch(res => {
				console.error(res);
			})
		},
		//根据名称精准查找用户 
		accurateQueryByName(state, name) {
			api.get('/user/accurateQueryByName', { params: { name: name } }).then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//模糊查找
		fuzzyQueryByName(state, name) {
			api.get('/user/fuzzyQueryByName', { params: { name: name } }).then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//根据id查找用户
		queryByID(state, id) {
			api.get('/user/queryByID', { params: { id: id } }).then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//更改名称
		setName(state, name) {
			console.log(name)
			apiFm.patch('/user/setName', { name: name }).then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//更改密码
		setPassword(state, password) {
			apiFm.patch('/user/setPassword', { password: password }).then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//用户注销
		cancel(state) {
			api.delete('/user/cancel').then(res => {
				console.log(res);
			}).catch(res => {
				console.log(res);
			})
		},
		//获取推荐post
		getPostList(state) {
			api.get('/post/queryPageOrderByTime', {
				params: {
					startNum: 0,//起始位置
					pageSize: 99999//页面大小
				}
			}).then(res => {
				//转成postList
				state.postList = apiFunc.getIDList(res.data.data);
			}).catch(res => {
				console.log(res);
			})
		},
		/**
		 * 发帖
		 *  postPayload:{
						"node": {
													 "content": {
													 "title": "登高",
													 "content": "渚青沙白鸟飞回"
													 }
									 }
						}
		 */
		
		post(state, postPayload) {
			if(state.userInfo.isLogin){
				postPayload.append('userId',state.userInfo.id)
				apiFm.post('/post/post', postPayload
				).then(res => {
					console.log(res);
				}).catch(err => {
					console.log(err);
				})
			}
			

		},
		comment(state,postPayload){
			if(state.userInfo.isLogin){
				postPayload.append('userId',state.userInfo.id)
				apiFm.post('/post/comment', postPayload
				).then(res => {
					console.log(res);
				}).catch(err => {
					console.log(err);
			})
			}
		},
		//关键词查找帖子
		queryByContent(state, content) {
			api.get('/post/queryByContent', {
				params: {
					content: content
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		queryPostById(state,id){
			api.get('/post/queryPostById', {
				params: {
					id: id
				}
			}).then(res => {
				//state.postList = res.data;
				if(res.data.code==1)
					return res.data.data;
				else
					return null;

			}).catch(err => {
				console.log(err);
			})
		},
		//根据作者ID查找帖子
		queryByAuthor(state, userId) {
			api.get('/post/queryByAuthor', {
				params: {
					userId: userId
				}
			}).then(res => {
				state.postList = res.data;
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//查找帖子
		queryPageOrderByTime(state, [startNum, pageSize]) {
			api.get('/post/queryPageOrderByTime', {
				params: {
					startNum: startNum,
					pageSize: pageSize
				}
			}).then(res => {
				state.postList = res.data.data.data;
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//删除帖子
		deletePost(state, id) {
			api.delete('/post/delete', {
				params: {
					id: id
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//分享帖子
		share(state, shareInfo) {
			apiFm.post('/rel/share', {
				postId: shareInfo.postId,
				shared: shareInfo.shared,
				shareNode: shareInfo.shareNode,
				userId: shareInfo.userId
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//取消分享
		cancelShare(state, cshInfo) {
			apiFm.delete('/rel/cancelShare', {
				data: {
					postId: cshInfo.postId,
					shared: cshInfo.shared,
					shareNode: cshInfo.shareNode
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//收藏帖子
		collect(state, collInfo) {
			apiFm.post('/rel/collect', {
				postId: collInfo.postId,
				collected: collInfo.collected,
				collectNode: collInfo.collectNode,
				userId: collInfo.userId
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//取消收藏帖子
		cancelCollect(state, ucollInfo) {
			apiFm.delete('/rel/cancelCollect', {
				data: {
					postId: ucollInfo.postId,
					collectNode: ucollInfo.collectNode
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//帖子点赞
		like(state, info) {
			console.log(info)
			apiFm.post('/rel/like', {

				postId: info.postId,
				collected: info.collected,
				collectNode: info.collectNode,
				liked: info.liked,
				likeNode: info.likeNode,
				userId: info.userId

			}
			).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		//取消帖子点赞
		cancelLike(state, info) {
			apiFm.delete('/rel/cancelLike',
				{
					data: {
						postId: info.postId,
						liked: info.liked,
						likeNode: info.likeNode
					}
				}
			).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		/**
		 * 创建节点
		 * node:{
		 *      "id":,(父节点id)
		 *      "type":,(节点类型)
		 *      "content":{
		 *              "title":,(节点名称)
		 *              "content":(节点注释)
		 *       }
		 * }
		 * 
		 */
		createNode(state, node) {
			api.post('/node/createNode', node
			).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		/**
		 * 移除节点 
		 * 1.移除该节点为父节点的所有relation
		 * 2.删除该节点
		 * 若parId不为空，则移除parId与该节点relation
		 * 
		 */
		removeNode(state, nodeInfo) {
			apiFm.delete('/node/removeNode', {
				data: {
					parId: nodeInfo.parId,
					nodeId: nodeInfo.nodeId
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		/**
		 * 更新节点content
		 * 可以更新title和content
		 * node:{
		 *      'id':,
		 *      'content':{
		 *              'title':,
		 *              'content':
		 *      }
		 * 
		 * }
		 */
		updateContent(state, node) {
			api.post('/node/updateContent', node).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		},
		/**
		 * 根据id获取节点content
		 * @param {*} param0 
		 */
		getContent(state, id) {
			api.get('/node/getContent', {
				params: {
					id: id
				}
			}).then(res => {
				console.log(res);
			}).catch(err => {
				console.log(err);
			})
		}

	},
	actions: {
		// 从后端获取用户信息的 action
    async fetchUserInfo({ commit }) {
      try {
        const response = await api.get('/user/getLoginState');
        const userInfo = response.data.data; // 根据你的 API 响应结构进行调整
				console.log(response)
				
				if(response.data.code==SUCESS){
					commit('setUserInfo', userInfo);
				}
					
      } catch (error) {
        console.error('获取用户信息时发生错误：', error);
      }
    },
		async initStore({ dispatch }) {
      await dispatch('fetchUserInfo');
    },
	},
	modules: {
	},
})


