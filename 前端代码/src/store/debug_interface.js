import test_case from "./test_case1";

export function dgetPost(id)
{
    return JSON.parse(JSON.stringify(test_case[id]))
}

export function dlogin(username, password)
{
    if (username == '0x80mem' && password == '123456')
        return 1
    if (username == 'ShacooKL' && password == '123456')
        return 2
    return 1;
}

export function dgetPostList()
{
    let list = []
    for (let i in test_case)
        if (test_case[i].type == 'post')
            list.push(test_case[i].id)
    console.log('list', list)
    return list
}