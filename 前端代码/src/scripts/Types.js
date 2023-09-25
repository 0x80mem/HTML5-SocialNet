import ShowLevel from "./ShowLevel";
import max from "core-js/fn/math"

//对于最简结构的显示规定
class Types
{
    /*
    父节点显示等级默认低于本节点一级
    子节点显示等级默认低于本节点一级
    par_unique: 父节点显示特殊规则
    chi_unique: 子节点显示特殊规则
    */
    constructor (par_unique, chi_unique)
    {
        this.par_policy = [];
        this.chi_policy = [];
        for (let i = 0; i < length(ShowLevel); i++)
        {
            this.par_policy.push(max(0, i - 1));
            this.chi_policy.push(max(0, i - 1));
        }
        par_unique.forEach(i => { this.par_policy[i[0]] = i[1] });
        chi_unique.forEach(i => { this.chi_policy[i[0]] = i[1] });
    }
}

export default Types;