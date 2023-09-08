# V1
## 一、用户
### 1.注册
	UserInfo register(userName,password);
### 2.登录
#### 2.1 id登录
		UserInfo loginByID(userID,password);
#### 2.2 用户名登录
		 UserInfo loginByName(userName,password);
### 3.修改用户信息
	UserInfo setUserInfo(userInfo);
### 4.筛选（查找）用户
#### 4.1 id
		User queryByID(userID);
#### 4.2 名称精准查询
		User accurateQueryByName(userName);
#### 4.3 名称模糊查询
		Users fuzzyQueryByName(userName);
### 5.注销用户
	int deleteUser();
### 6.登出
	int logout();
## 二、帖子相关
### 1.发帖
	int post(postContent,userID);
### 2.删帖
	int deletePost(postID,userID);
### 3.筛选（查找）帖子
#### 3.1 关键字
		posts query(content);
#### 3.2 参与者
		posts queryByUserID(userID);
#### 3.3 时间
		posts queryByTime(startNum,endNum);
## 4.转发
    	int sharePost(postID,userID);

