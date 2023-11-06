import Types from "./Types"
import ShowLevel from "./ShowLevel"
const TypeConfig = {
    //推文, 最常见的类型
    "post": new Types([
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'], ShowLevel['hide']],
        [ShowLevel['full'], ShowLevel['title']],
    ],
    [
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'],    ShowLevel['hide']],
        [ShowLevel['full'],   ShowLevel['full']]
    ]),
    //转发, 与推文相同
    "forward": new Types([
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'], ShowLevel['hide']],
        [ShowLevel['full'], ShowLevel['title']],
    ],
    [
        [ShowLevel['author'], ShowLevel['hide']],
        [ShowLevel['zip'],    ShowLevel['hide']],
        [ShowLevel['full'],   ShowLevel['full']]
    ]),
    //版本, post, forward的父节点
    //version只有hide, title, full
    //version的父节点应为空
    //与主题等级相同
    "version": new Types([
        [ShowLevel['full'], ShowLevel['title']],
    ],
    [
        [ShowLevel['full'], ShowLevel['title']],
    ]
    ),
    /* 用户内容 */
    //用户信息
    //用户信息中author和title对内容显示没有影响，但会影响父子节点显示
    "user": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']],
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //用户信息-详细页面
    "userInfo": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']],
    ],
    [
        [ShowLevel['full'],   ShowLevel['full']]
    ]),
    //点赞 无分组
    "like": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['zip']]
    ]),
    "comment": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['zip']]
    ]),
    //关注
    "subscribe": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //粉丝
    "fans": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //收藏
    "collection": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['zip']],
        [ShowLevel['full'],   ShowLevel['zip']]
    ]),
    /* Post子信息 */
    //点赞 无分组
    "liked": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //收藏
    "collected": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    //转发
    "forwarded": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
    "shared": new Types([
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ],
    [
        [ShowLevel['zip'],    ShowLevel['title']],
        [ShowLevel['full'],   ShowLevel['title']]
    ]),
}

export default TypeConfig
