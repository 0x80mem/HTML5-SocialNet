import Types from "./Types"
import ShowLevel from "./ShowLevel"
const TypeConfig = {
    //推文, 最常见的类型
    "post": Types([
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'], ShowLevel['hide']],
        [ShowLevel['full'], ShowLevel['title']],
    ], 
    [
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'],    ShowLevel['hide']],
        [ShowLevel['full'],   ShowLevel['full']]
    ]),
    //版本, post的父节点
    //version只有hide, title, full
    //version的父节点应为空
    //与主题等级相同
    "version": Types([
        [ShowLevel['full'], ShowLevel['title']],
    ],
    [
        [ShowLevel['full'], ShowLevel['title']],
    ]
    ),
    //用户信息
    //用户信息中author和title对内容显示没有影响，但会影响父子节点显示
    "user": Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']],
    ], 
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //关注
    "subscribe": Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ], 
    [
        [ShowLevel['zip'],    ShowLevel['zip']],
        [ShowLevel['full'],   ShowLevel['zip']]
    ]),
}

export default TypeConfig