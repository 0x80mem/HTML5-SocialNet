import ShowLevel from "./ShowLevel"
import TypeConfig from "./TypeConfig"

async function Expand(post, visit, showLevel, getFunc, deleteFunc) {

    if (post.showLevel == showLevel)
        return post;
    post.showLevel = showLevel;

    if (post.showLevel == ShowLevel['hide']) {
        let id = post.id;
        deleteFunc(post.id);
        return id;
    }

    if (post.chiPost.length == 0)
        return post;

    let type = TypeConfig[post.type];
    console.log("type is",type)
    let chiShow = type.chi_policy[showLevel];

    if (typeof post.chiPost[0] == 'number') {
        let chilist = [...post.chiPost];
        post.chiPost = [];
        if (chiShow > ShowLevel['hide']) {
            for (let id of chilist) {
                if (!visit.has(id)) {
                    visit.add(id);
                    let childPost = null;
                    if(typeof id =='number'){
                        childPost = await getFunc(id, chiShow);
                    }else{
                        childPost = await getFunc(id.id, chiShow);
                    }
                    childPost.chiPost.push(childPost);
                    await Expand(childPost.chiPost[childPost.chiPost.length - 1], visit, chiShow, getFunc, deleteFunc);
                }
            }
        }
    } else {
        let chiNow = post.chiPost[0].showLevel;
        if (chiNow !== chiShow) {
            let chilist = [...post.chiPost];
            post.chiPost = [];
            for (let chi of chilist) {
                post.chiPost.push(await Expand(chi, visit, chiShow, getFunc, deleteFunc));
            }
        }
    }
    return post;
}

export default Expand;
