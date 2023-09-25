//一个post的最简结构
class Post
{
    /*
    id: post的唯一标识
    type: 显示类型, 字符串，由TypeConfig解析
    content: 内容, Content类
    parPost: 父节点, post id
    chiPost: 子节点, post id
    编辑时显示: 
    blackList: 黑名单, post id 暂不考虑
    whiteList: 白名单, post id 暂不考虑
    editGroup: 编辑组, {post id: rights} 暂不考虑
    baseRight: 基础编辑权限
    */
    constructor (id, type, content, parPost, chiPost, blackList, whiteList, editGroup, baseRight = 'banned')
    {
        this.id = id;
        this.type = type;
        this.content = content;
        this.parPost = parPost;
        this.chiPost = chiPost;
        this.blackList = blackList;
        this.whiteList = whiteList;
        this.editGroup = editGroup;
        this.baseRight = baseRight;
    }
}


export default Post;