import Content from "@/scripts/Content";
import Post from "@/scripts/Post";

// 测试用例
// 一个0x80mem和ShacooKL的互关情形
const test_case = {
    1: new Post(
        1,
        'user',
        1,
        new Content('0x80mem', false, ''),
        [],
        [3, 5, 7],
    ),
    2: new Post(
        2,
        'user',
        2,
        new Content('ShacooKL', false, ''),
        [],
        [4, 6, 8],
    ),
    3: new Post(
        3,
        'subscribe',
        1,
        new Content('关注', false, ''),
        [1],
        [2],
    ),
    4: new Post(
        4,
        'subscribe',
        2,
        new Content('关注', false, ''),
        [2],
        [1],
    ),
    5: new Post(
        5,
        'fans',
        1,
        new Content('粉丝', false, ''),
        [1],
        [2],
    ),
    6: new Post(
        6,
        'fans',
        2,
        new Content('粉丝', false, ''),
        [2],
        [1],
    ),
    7: new Post(
        7,
        'post',
        1,
        new Content('学习',false,'学习编程'),
        [1],
        [9],
    ),
    8: new Post(
        8,
        'post',
        2,
        new Content('如何',false,'如何学习编程学习编程是具有挑战性的任务。无论你是完全初学者还是有一一项有趣而具学者还是有一些经验的编性的任务。'),
        [2],
        [10],
    ),
    9: new Post(
        9,
        'post',
        1,
        new Content('如何学习编程',false,'学习编程是一项有趣而具有挑战性的任务。无论你是完全初学者还是有一些经验的编程爱好者，都可以采取一些有效的步骤来提高自己的编程技能。选择合适的编程语言： 首先，你需要选择一门编程语言。如果你是初学者，Python 可能是一个不错的选择，因为它具有简单的语法和丰富的资源。如果你对 Web 开发感兴趣，可以学习 HTML、CSS 和 JavaScript。阅读文档和教程： 学习编程的最佳方法之一是阅读相关的文档和教程。官方文档和在线教程可以帮助你了解语言的基础知识和最佳实践。编写代码： 不要只是 passively 阅读代码，一定要积极地编写自己的项目。编程是一项实践性强的技能，通过亲自动手编写代码，你可以加深对编程概念的理解。参与开源项目： 如果你感到自信，可以考虑参与开源项目。这将为你提供与其他开发者合作的机会，学习他们的经验和技巧。寻找反馈： 不要害怕寻找反馈。无论是从朋友、同事还是在线社区中，都可以获得有价值的建议和意见。坚持不懈： 编程是一个长期学习的过程。不要气馁，坚持不懈，每天都进步一点点。'),
        [7],
        [],
    ),
}

export default test_case;