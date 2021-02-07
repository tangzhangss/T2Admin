//--------------------------------------------------------------------------------------------------------
/*
对象数组转树型数组对象
array 用于连接的key
由底向上（parent_id）
*/
export function arrayToTree(array, key){
  let res=[];
  //先找出根元素
  for(let i = array.length - 1; i >= 0; i--){
    let item = array[i];
    if(!item[key]){
      res.push(item);
      array.splice(i,1);//删除这个item
    }
  }
  arrayToTreeAssist(res,array,key);
  //res是最后的结果
  return res;
}
function arrayToTreeAssist(res,array,key){
  //没有元素了
  if(array.length==0)return false;
  //这里需要倒叙遍历因为需要删除子元素
  for(let i = array.length - 1; i >= 0; i--){
    let item = array[i];
    res.some(item2=>{
      if(item[key]==item2.id){
        if(!item2.children)item2.children=[];
        item2.children.push(item);
        array.splice(i,1);//删除这个item
        return true;
      }
      if(item2.children){//有至少一个子元素
        //查询children中是否存在当前元素的父级
        let res = arrayToTreeAssist2(item2.children,item,key);
        if (res!==undefined){//这里可能索引是0
          array.splice(i,1);//删除这个item
          return true;
        }
      }
    })
  }
  //一直循环，知道array里面的元素为空
  arrayToTreeAssist(res,array,key);
}
function arrayToTreeAssist2(children,item,key){
  for (let i=0;i<children.length;i++){
    let c = children[i];
    if(c.id==item[key]){
      if(!c.children) c.children=[];
      c.children.push(item);
      return i;
    }
    if(c.children){
      arrayToTreeAssist2(c.children,item,key);
    }
  }
}

/*
获取不存在字节点的节点
 */
export  function getLeafNode(treeData){
  let node = [];
  updateLeafNode(treeData,node);
  return node;
}
function updateLeafNode(treeData,node) {
  treeData.forEach(item=>{
    if(item.children){
      updateLeafNode(item.children,node);
    }else{
      node.push(item);
    }
  })
}

