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
        [3, 5],
    ),
    2: new Post(
        2,
        'user',
        2,
        new Content('ShacooKL', false, ''),
        [],
        [4, 6],
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
}

export default test_case;