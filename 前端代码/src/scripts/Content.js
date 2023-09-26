//post的内容中影响表现的属性
class Content
{
    /*
    title: 标题
    has_menu: 拥有目录
    content: 内容
    */
    constructor (title, has_menu, content)
    {
        this.title = title
        this.has_menu = has_menu
        this.content = content
    }
}

export default Content;