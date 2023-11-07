import ShowLevel from "./ShowLevel";
import TypeConfig from "./TypeConfig";

let visit = new Set();

async function RecursiveNode(post, showLevel, getFunc) {
    if (visit.has(post.id)) 
        return;
    visit.add(post.id);

    post.showLevel = showLevel;
    let type = TypeConfig[post.type];
    console.log('RecursiveNode post:',post)
    let parList = [...post.parPost];
    let parShow = type.par_policy[showLevel];
    if (parShow > ShowLevel['hide']) {
        post.parPost = [];
        for (const id of parList) {
            post.parPost.push(await getFunc(id, parShow));
            await RecursiveNode(post.parPost[post.parPost.length - 1], parShow, getFunc);
        }
    }

    let chilist = [...post.chiPost];
    let chiShow = type.chi_policy[showLevel];
    if (chiShow > ShowLevel['hide']) {
        post.chiPost = [];
        for (const id of chilist) {
            post.chiPost.push(await getFunc(id, chiShow));
            await RecursiveNode(post.chiPost[post.chiPost.length - 1], chiShow, getFunc);
        }
    }
}

async function Recursive(post, showLevel, getFunc) {
    visit.clear();
    showLevel = ShowLevel[showLevel];
    await RecursiveNode(post, showLevel, getFunc);
    return visit;
}

export default Recursive;
