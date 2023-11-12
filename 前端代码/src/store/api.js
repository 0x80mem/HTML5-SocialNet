import Post from '@/scripts/Post'
import Content from '@/scripts/Content'
import * as StatusCodes from './statusCodes'
import store from '.'
import { api,apiFm } from './axiosConfig'
/* eslint-disable */
function convertPostVToPost(postV) {
	
	// 创建一个新的 Post 对象，以 PostV 的属性填充它
	const post = new Post(
		postV.id,
		postV.type,
		postV.author,
		new Content(postV.content.title, postV.content.has_menu, postV.content.content),
		postV.parPost,
		postV.chiPost,
		postV.blackList,
		postV.whiteList,
		postV.grantGroup,
		postV.editGroup,
		postV.baseRight
	);
	return post;
}

export function getIDList(postVList) {
	const postList = postVList.map((item) => item.id);
	console.log('postList',postList)
  return postList;
}
// 统一的异常处理函数
async function handleRequest(apiFunction, dataHandler, identity) {
  try {
		let response = null;
		if(identity==null){
			if(!store.state.userInfo.isLogin){
				return;
			}
		}
    response = await apiFunction();
		if(response.data.code==StatusCodes.SUCESS)
    	return dataHandler(response.data);
		else
			return null;
  } catch (error) {
    //console.error(error);
    return null;
  }
}



export async function getPost(id) {
  const apiFunction = () => api.get('/node/getNode', { params: { id } });
  const dataHandler = (data) => convertPostVToPost(data.data);
  return handleRequest(apiFunction, dataHandler,false);
}


export async function like(postId) {
	const apiFunction = () =>apiFm.post('/rel/like', {
		postId: postId,
		userId: store.state.userInfo.id
	});
	const dataHandler = (data) => convertPostVToPost(data.data);
	return handleRequest(apiFunction, dataHandler);
}

export async function uLike(postId) {
	const apiFunction = () =>apiFm.delete('/rel/cancelLike', {
		data: {
			postId: postId,
			userId: store.state.userInfo.id
		}
	});
	const dataHandler = (data) => convertPostVToPost(data.data);
	return handleRequest(apiFunction, dataHandler);
}

export async function isLikedPost(postId) {
	const apiFunction = () =>apiFm.get('/rel/beLiked', {
		params: {
			postId: postId,
			userId: store.state.userInfo.id
		}
	});
	const dataHandler = (data) => data.data
	return handleRequest(apiFunction, dataHandler);
}

export async function comment(comment) {
	if (typeof comment.author != 'number') {
		comment.author = comment.author[0]
	}
	const apiFunction = () =>api.post('/node/createNode', comment);
	const dataHandler = (data) => data.code
	return handleRequest(apiFunction, dataHandler);
}

export async function collect(postId) {
	const apiFunction = () =>apiFm.post('/rel/collect', {
		postId: postId,
		userId: store.state.userInfo.id
	});
	const dataHandler = (data) => convertPostVToPost(data.data)
	return handleRequest(apiFunction, dataHandler);
}
export async function uCollect(postId) {
	const apiFunction = () =>apiFm.delete('/rel/cancelCollect', {
		data: {
			postId: postId,
			userId: store.state.userInfo.id
		}
	});
	const dataHandler = (data) => convertPostVToPost(data.data);
	return handleRequest(apiFunction, dataHandler);
}
export async function isCollectedPost(postId) {
	const apiFunction = () =>apiFm.get('/rel/beCollected', {
		params: {
			postId: postId,
			userId: store.state.userInfo.id
		}
	});
	const dataHandler = (data) => data.data
	return handleRequest(apiFunction, dataHandler);
}

export async function getPostListByAuthor(userId) {
	const apiFunction = () =>apiFm.get('/post/queryByAuthor', {
		params: {
			userId: userId
		}
	});
	const dataHandler = (data) =>getIDList(data.data) 
	return handleRequest(apiFunction, dataHandler);
}
export async function getUserInfo(userId) {
	const apiFunction = () =>apiFm.get('/user/getUserInfo', {
		params: {
			id: userId
		}
	});
	const dataHandler = (data) => data.data
	return handleRequest(apiFunction, dataHandler);
}

export async function isSubscribed(userId) {
	const apiFunction = () =>apiFm.get('/rel/beSubscribed', {
		params: {
			hostId: store.state.userInfo.id,
			userId:userId
		}
	});
	const dataHandler = (data) => data.data
	return handleRequest(apiFunction, dataHandler);
}

export async function sub(userId) {
	const apiFunction = () =>apiFm.post('/rel/subscribe', {
		userToSub: userId,
		user: store.state.userInfo.id
	});
	const dataHandler = (data) => {
		if(data.code==StatusCodes.SUCESS)
			return true;
		return false;
	};
	return handleRequest(apiFunction, dataHandler);
}

export async function uSub(userId) {
	const apiFunction = () =>apiFm.delete('/rel/cancelSubscribe', {
		data: {
			userToSub: userId,
			user: store.state.userInfo.id
		}
	});
	const dataHandler = (data) =>{
		if(data.code==StatusCodes.SUCESS)
			return true;
		return false;
	};
	return handleRequest(apiFunction, dataHandler);
}

export async function changeAvatar(data) {
	const apiFunction = () =>apiFm.post('/user/changeAvatar', data);
	const dataHandler = (data) =>{
		if(data.code==StatusCodes.SUCESS)
			return data.data;
		return null;
	};
	return handleRequest(apiFunction, dataHandler);
}
export async function changeInfo(data) {
	const apiFunction = () =>api.post('/user/changeInfo', data);
	const dataHandler = (data) =>{
		if(data.code==StatusCodes.SUCESS)
			return true;
		return false;
	};
	return handleRequest(apiFunction, dataHandler);
}

export async function getPostListByContent(content) {
	const apiFunction = () =>api.get('/post/queryByContent', {
		params: {
			content: content
		}
	});
	const dataHandler = (data) =>getIDList(data.data) 
	return handleRequest(apiFunction, dataHandler,false);
}