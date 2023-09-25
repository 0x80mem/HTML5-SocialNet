//post的内容中影响表现的属性
class Content
{
    /*
    title: 标题
    author: 作者, 
            id: 单个作者
            post: 多个作者 未来实现
    has_menu: 拥有目录
    content: 内容
    */
    constructor (title, author, has_menu, content)
    {
        this.title = title
        this.author = author
        this.has_menu = has_menu
        this.content = content
    }
}

export default Content;