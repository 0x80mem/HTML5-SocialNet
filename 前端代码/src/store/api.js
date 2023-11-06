import Post from '@/scripts/Post'
import Content from '@/scripts/Content'
import axios from 'axios'
import store from '../store';
/* eslint-disable */
var api = axios.create({
	baseURL: 'http://47.93.10.201/api',
	// baseURL: "http://localhost:8088",
	timeout: 1000,
})
var apiFm = axios.create({
	// baseURL: "http://localhost:8088",
	baseURL: 'http://47.93.10.201/api',
	timeout: 1000,
	headers: {
		'Content-Type': 'application/x-www-form-urlencoded'
	},
})
function convertPostVToPost(postV) {
	// 创建一个新的 Post 对象，以 PostV 的属性填充它
	const post = new Post(
			postV.id,
			postV.type,
			postV.author,
			new Content(postV.content.title,postV.content.has_menu,postV.content.content),
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

export async function getPost(id) {
  try {
			const response = await api.get('/node/getNode',{
				params: {
					id: id
				}
		});
		return convertPostVToPost(response.data.data); // 返回从后端获取的数据
  } catch (error) {
    throw error;
  }
}


export function convertListVToPost(postVList) {
	// 创建一个新的 Post 对象，以 PostV 的属性填充它
	const postList=[]
	for (const item of postVList) {
		postList.push(item.id)
	}
	return postList;
}


export async function like(postId) {
  try {
    const response = await apiFm.post('/rel/like', {
      postId: postId,
      // userId: store.state.userInfo.id
			userId: 1803090083
    });
		console.log("like res is:",response)
    if (response.data.code === 1) {
      return convertPostVToPost(response.data.data);
    } else {
			console.log("response msg:",response.data.msg)
      return null;
    }
  } catch (error) {
    console.error(error);
		return null;
  }
}





export async function uLike(postId) {
  try {
    const res = await apiFm.delete('/rel/cancelLike', {
      data: {
        postId: postId,
        // userId: store.state.userInfo.id
				userId: 1803090083
      }
    });

    if (res.data.code === 1) {
      return convertPostVToPost(res.data.data);
    } else {
      return null;
    }
  } catch (err) {
    console.log(err);
    return null; // 处理错误并返回null
  }
}
