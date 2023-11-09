import Post from '@/scripts/Post'
import Content from '@/scripts/Content'
import axios from 'axios'
import * as StatusCodes from './statusCodes'
import store from '.'
/* eslint-disable */
// const baseURL ="http://localhost:8088"
const baseURL= 'http://47.93.10.201/api'
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
  return postList;
}
// 统一的异常处理函数
async function handleRequest(apiFunction, dataHandler) {
  try {
    const response = await apiFunction();
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
  return handleRequest(apiFunction, dataHandler);
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
