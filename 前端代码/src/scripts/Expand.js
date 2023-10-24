import ShowLevel from "./ShowLevel"
import TypeConfig from "./TypeConfig"

// 目前改变子节点显示不影响父节点
function Expand(post, visit, showLevel, getFunc, deleteFunc)
{
    // 减少无意义访问访问
    if (post.showLevel == showLevel)
        return post
    post.showLevel = showLevel

    // 隐藏处理
    if (post.showLevel == ShowLevel['hide'])
    {
        let id = post.id
        deleteFunc(post.id)
        return id
    }

    if (post.chiPost.length == 0)
        return post

    // 递归子节点
    let type = TypeConfig[post.type]
    let chiShow = type.chi_policy[showLevel]
    // 无子节点
    if (typeof post.chiPost[0] == 'number')
    {
        let chilist = [...post.chiPost]
        post.chiPost = []
        if (chiShow > ShowLevel['hide'])
        {
            chilist.forEach(id => {
                if (!visit.has(id))
                {
                    visit.add(id)
                    post.chiPost.push(getFunc(id, chiShow))
                    Expand(post.chiPost[post.chiPost.length - 1], visit, chiShow, getFunc, deleteFunc)
                }
            });
        }
    }
    else // 有子节点
    {
        let chiNow = post.chiPost[0].showLevel
        if (chiNow !== chiShow)
        {
            let chilist = [...post.chiPost]
            post.chiPost = []
            chilist.forEach(chi => {
                post.chiPost.push(Expand(chi, visit, chiShow, getFunc, deleteFunc))
            });
        }
    }
    return post
}

export default Expand
