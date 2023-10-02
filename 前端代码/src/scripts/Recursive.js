import ShowLevel from "./ShowLevel"
import TypeConfig from "./TypeConfig"

let visit = new Set()

// 展开Post父子节点，如果显示等级不足，将会保留id
// 追加显示等级
// post: 要展开的post
// showLevel: 显示等级
// getFunc: 请求post
function RecursiveNode(post, showLevel, getFunc)
{
    // 防止重复访问
    if (visit.has(post.id)) 
        return
    visit.add(post.id)

    // 追加显示等级
    post.showLevel = showLevel
    // 父子节点未来可能有不同处理方式
    let type = TypeConfig[post.type]

    // 递归父节点
    let parList = [...post.parPost]
    let parShow = type.par_policy[showLevel]
    if (parShow > ShowLevel['hide'])
    {
        post.parPost = []
        parList.forEach(id => {
            post.parPost.push(getFunc(id, parShow))
            RecursiveNode(post.parPost[post.parPost.length - 1], parShow, getFunc)
        });
    }

    // 递归子节点
    let chilist = [...post.chiPost]
    let chiShow = type.chi_policy[showLevel]
    if (chiShow > ShowLevel['hide'])
    {
        post.chiPost = []
        chilist.forEach(id => {
            post.chiPost.push(getFunc(id, chiShow))
            RecursiveNode(post.chiPost[post.chiPost.length - 1], chiShow, getFunc)
        });
    }
}


function Recursive(post, showLevel, getFunc)
{
    visit.clear()
    showLevel = ShowLevel[showLevel]
    RecursiveNode(post, showLevel, getFunc)
    return visit
}

export default Recursive