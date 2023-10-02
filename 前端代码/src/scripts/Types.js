import ShowLevel from "./ShowLevel";
import { Math } from "core-js";

//不同类型的属性
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
        for (let level in ShowLevel)
        { 
            this.par_policy.push(Math.max(ShowLevel['hide'], ShowLevel[level] - 1));
            this.chi_policy.push(Math.max(ShowLevel['hide'], ShowLevel[level] - 1));
        }
        par_unique.forEach(i => { this.par_policy[i[0]] = i[1] });
        chi_unique.forEach(i => { this.chi_policy[i[0]] = i[1] });
    }
}

export default Types;